package project.pipepipe.shared.job

import io.ktor.client.statement.*
import project.pipepipe.shared.SharedContext
import project.pipepipe.shared.database.DatabaseOperations
import project.pipepipe.shared.downloader.Downloader
import project.pipepipe.shared.getQueryValue
import project.pipepipe.shared.infoitem.CookieInfo
import project.pipepipe.shared.infoitem.Info
import project.pipepipe.shared.state.CachedExtractState
import project.pipepipe.shared.state.PreFetchPayloadState
import kotlin.io.encoding.Base64

suspend fun executeJobFlow(
    jobType: SupportedJobType,
    url: String?,
    serviceId: String?,
    payload: String? = null
): ExtractResult<out Info, out Info> {
    val cookieManager = SharedContext.cookieManager
    val sessionManager = SharedContext.sessionManager

    if (url?.startsWith("cache://") == true) {
        return (sessionManager.loadState(url.substringAfter("cache://")) as CachedExtractState).data
    }

    if (jobType != SupportedJobType.REFRESH_COOKIE && serviceId != null && cookieManager.isCookieExpired(serviceId)) {
        val response = executeJobFlow(
            SupportedJobType.REFRESH_COOKIE, null, serviceId
        )
        cookieManager.setCookieInfo(serviceId, response.info as CookieInfo)
    }
    val cookie = if (serviceId != null)cookieManager.getCookie(serviceId) else null
    val state = if (url?.contains("cacheId=") == true) {
        sessionManager.loadState(getQueryValue(url, "cacheId")!!)
    } else payload?.let { PreFetchPayloadState(-1, payload) }
    var currentRequest = JobRequest(
        sessionId = null,
        jobType = jobType,
        url = url,
        results = null,
        serviceId = serviceId,
        cookie = cookie,
        state = state
    )

    while (true) {
        val response = SharedContext.serverRequestHandler(currentRequest)
        when (response.status) {
            JobStatus.COMPLETE -> {
                if (response.state != null) {
                    sessionManager.saveState(response.sessionId, response.state)
                }
                return response.result !!
            }
            JobStatus.CONTINUE -> {
                val tasks = response.tasks ?: throw kotlin.IllegalStateException("Job must continue but no tasks were provided")
                val taskResults = executeClientTasksConcurrent(tasks)

                // Cache the state on client side
                if (response.state != null) {
                    sessionManager.saveState(response.sessionId, response.state)
                }

                currentRequest = JobRequest(
                    sessionId = response.sessionId,
                    jobType = jobType,
                    url = url,
                    results = taskResults,
                    serviceId = serviceId,
                    cookie = cookie,
                    state = response.state
                )
            }

            JobStatus.FAILED -> {
                val fatalError = response.result!!.fatalError!!
                val errorId = DatabaseOperations.insertErrorLog(
                    stacktrace = fatalError.stackTrace,
                    task = currentRequest.jobType.name,
                    errorCode = fatalError.code,
                    request = currentRequest.url
                )
                return response.result.copy(fatalError = fatalError.copy(errorId = errorId))
            }
        }
    }
}

suspend fun executeClientTasksConcurrent(
    tasks: List<ClientTask>,
    concurrency: Int = 10
): List<TaskResult> {
    val downloader = SharedContext.downloader
    val httpRequests = tasks.map { task ->
        Downloader.HttpRequest(
            request = {
                when (task.payload.method) {
                    RequestMethod.GET -> {
                        downloader.get(task.payload.url, task.payload.headers)
                    }
                    RequestMethod.POST -> {
                        if (task.payload.body != null) {
                            downloader.postJson(
                                url = task.payload.url,
                                json = task.payload.body,
                                headers = task.payload.headers
                            )
                        } else {
                            downloader.post(
                                url = task.payload.url,
                                headers = task.payload.headers
                            )
                        }
                    }
                }
            },
            onSuccess = { response ->
                TaskResult(
                    taskId = task.taskId,
                    result = if (!task.payload.shouldReturnBase64Bytes)response.bodyAsText() else Base64.encode(response.bodyAsBytes()),
                    responseHeader = response.headers.entries().associate { it.key to it.value }
                )
            },
            onError = { exception ->
                exception.printStackTrace()
                TaskResult(
                    taskId = task.taskId,
                    result = null,
                    responseHeader = null,
                )
            }
        )
    }

    val results = downloader.executeAll(httpRequests, concurrency)
    return results.mapNotNull { it.getOrNull() as? TaskResult }
}
