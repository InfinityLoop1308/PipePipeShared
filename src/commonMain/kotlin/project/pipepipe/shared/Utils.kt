package project.pipepipe.shared

import org.ocpsoft.prettytime.PrettyTime
import project.pipepipe.shared.SharedContext.appLocale
import project.pipepipe.shared.infoitem.helper.SearchType
import java.net.URI
import java.net.URL
import java.net.URLEncoder
import java.util.Date

fun Long.toText(isMillis: Boolean = false): String {
    var totalSeconds = this
    if (isMillis) totalSeconds /= 1000
    val days = totalSeconds / 86400
    val hours = (totalSeconds % 86400) / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return buildString {
        if (days > 0) {
            append(String.format("%02d:", days))
        }
        if (hours > 0 || days > 0) {
            append(String.format("%02d:", hours))
        }
        append(String.format("%02d:%02d", minutes, seconds))
    }
}


fun formatCount(count: Long?): String {
    return when {
        count == null -> "..."
        count > 1_000_000_000 -> "%.1fB".format(count / 1_000_000_000.0)
        count > 1_000_000 -> "%.1fM".format(count / 1_000_000.0)
        count > 1_000 -> "%.1fK".format(count / 1_000.0)
        else -> count.toString()
    }
}

fun generateQueryUrl(query: String, searchType: SearchType): String = buildString {
    append(searchType.baseUrl)
    append(URLEncoder.encode(query, "UTF-8"))
    searchType.availableSearchFilterGroups?.forEach { group ->
        group.selectedSearchFilters.forEach {
            val separator = if (contains("?")) "&" else "?"
            append(separator).append(it.parameter)
        }
    }
}

fun formatRelativeTime(timestampMillis: Long): String {
    return PrettyTime(appLocale).format(Date(timestampMillis))
}

fun getQueryValue(url: String, key: String): String? {
    return getQueryValue(stringToURI(url)!!, key)
}

fun getQueryValue(url: URI, key: String): String? {
    val query = url.query ?: return null
    val pairs = query.split("&")
    for (pair in pairs) {
        val keyValue = pair.split("=", limit = 2)
        if (keyValue.size == 2 && keyValue[0] == key) {
            return keyValue[1]
        }
    }
    return null
}

fun replaceQueryValue(url: String, key: String, value: String): String? {
    val uri = stringToURI(url) ?: return null

    val query = uri.query
    val newQuery = if (query.isNullOrEmpty()) {
        "$key=$value"
    } else {
        val pairs = query.split("&").toMutableList()
        var found = false
        for (i in pairs.indices) {
            val keyValue = pairs[i].split("=", limit = 2)
            if (keyValue.isNotEmpty() && keyValue[0] == key) {
                pairs[i] = "$key=$value"
                found = true
                break
            }
        }

        if (!found) {
            pairs.add("$key=$value")
        }

        pairs.joinToString("&")
    }

    return URI(
        uri.scheme,
        uri.userInfo,
        uri.host,
        uri.port,
        uri.path,
        newQuery,
        uri.fragment
    ).toString()
}


fun stringToURI(urlString: String): URI? {
    return runCatching { URI(urlString) }.getOrNull()
}