package project.pipepipe.shared.infoitem

import kotlinx.serialization.Serializable
import project.pipepipe.shared.infoitem.helper.SearchType
import project.pipepipe.shared.job.Payload

@Serializable
enum class ExternalUrlType {
    STREAM,
    CHANNEL,
    PLAYLIST
}

@Serializable
data class SupportedServiceInfo(
    val serviceId: String,
    val availableSearchTypes: List<SearchType>? = null,
    val suggestionPayload: Payload? = null,
    val suggestionStringPath: Pair<String, String>? = null,
    val suggestionJsonBetween: Pair<String, String>? = null,
    val trendingList: List<TrendingInfo> = emptyList(),
    val feedFetchInterval: Int = 0,
    val themeColor: String = "#FFFFFF",
    val urlPatterns: Map<ExternalUrlType, List<String>> = emptyMap()
): Info
