package project.pipepipe.shared.job

import kotlinx.serialization.Serializable
import project.pipepipe.shared.infoitem.Info
import project.pipepipe.shared.state.State
@Serializable
data class JobRequest(
    val sessionId: String? = null,
    val jobType: SupportedJobType,
    val serviceId: Int? = null,
    val url: String? = null,
    val results: List<TaskResult>? = null,
    val cookie: String? = null,
    val state: State? = null
)

@Serializable
data class JobResponse<META: Info,DATA : Info>(
    val sessionId: String,
    val status: JobStatus,
    val tasks: List<ClientTask>? = null,
    val result: ExtractResult<META,DATA>? = null,
    val state: State? = null // state to be cached on client side
)

@Serializable
enum class JobStatus {
    CONTINUE,
    COMPLETE,
    FAILED
}
@Serializable
data class ClientTask(
    val taskId: String = "default",
    val payload: Payload
)

@Serializable
data class TaskResult(
    val taskId: String,
    val result: String?,
    val responseHeader: Map<String, List<String>>?
)

@Serializable
data class Payload(
    val method: RequestMethod,
    val url: String,
    val headers: Map<String, String>? = null,
    val body: String? = null,
    val shouldReturnBase64Bytes: Boolean = false
)

enum class RequestMethod {
    GET,
    POST
}

sealed class JobStepResult {
    data class ContinueWith(val tasks: List<ClientTask>, val state: State? = null) : JobStepResult()
    data class CompleteWith<META: Info,DATA: Info>(val result: ExtractResult<META, DATA>, val state: State? = null) : JobStepResult()
    data class FailWith(val fatalError: ErrorDetail) : JobStepResult()
}

fun String.isDefaultTask(): Boolean = this == "default"