package project.pipepipe.shared.downloader

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.head
import io.ktor.client.request.header
import io.ktor.client.request.options
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.content.OutgoingContent
import io.ktor.http.content.TextContent
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

class Downloader(
    private val client: HttpClient
){
    suspend fun get(url: String, headers: Map<String, String>? = null): HttpResponse =
        client.get(url) { applyHeaders(headers) }

    suspend fun post(
        url: String,
        headers: Map<String, String>? = null,
        body: OutgoingContent? = null
    ): HttpResponse = client.post(url) {
        applyHeaders(headers)
        body?.let { setBody(it) }
    }

    suspend fun head(url: String, headers: Map<String, String>? = null): HttpResponse =
        client.head(url) { applyHeaders(headers) }

    suspend fun options(url: String, headers: Map<String, String>? = null): HttpResponse =
        client.options(url) { applyHeaders(headers) }

    suspend fun postText(
        url: String,
        text: String,
        contentType: ContentType = ContentType.Text.Plain,
        headers: Map<String, String>? = null
    ): HttpResponse = post(url, headers, TextContent(text, contentType))

    suspend fun postJson(
        url: String,
        json: String,
        headers: Map<String, String>? = null
    ): HttpResponse = post(url, headers, TextContent(json, ContentType.Application.Json))

    suspend fun postForm(
        url: String,
        params: Map<String, String>,
        headers: Map<String, String>? = null
    ): HttpResponse = post(url, headers, FormDataContent(Parameters.Companion.build {
        params.forEach { (key, value) -> append(key, value) }
    }))

    data class HttpRequest(
        val request: suspend () -> HttpResponse,
        val onSuccess: suspend (HttpResponse) -> Any?,
        val onError: suspend (Exception) -> Any? = { it.printStackTrace(); null }
    )

    suspend fun executeAll(
        requests: List<HttpRequest>,
        concurrency: Int = 10
    ): List<Result<Any?>> = coroutineScope {
        val semaphore = Semaphore(concurrency)
        requests.map { httpRequest ->
            async {
                semaphore.withPermit {
                    try {
                        Result.success(httpRequest.onSuccess(httpRequest.request()))
                    } catch (e: Exception) {
                        Result.success(httpRequest.onError(e))
                    }
                }
            }
        }.awaitAll()
    }

    private fun HttpRequestBuilder.applyHeaders(headers: Map<String, String>?) {
        headers?.forEach { (name, value) -> header(name, value) }
    }
}