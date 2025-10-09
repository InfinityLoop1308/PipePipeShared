package project.pipepipe.shared.infoitem

import kotlinx.serialization.Serializable
import project.pipepipe.shared.infoitem.helper.SearchFilterGroup
import project.pipepipe.shared.infoitem.helper.SearchFilterItem
import project.pipepipe.shared.infoitem.helper.SearchType
import project.pipepipe.shared.job.Payload

@Serializable
data class SupportedServiceInfo(
    val serviceId: String,
    val availableSearchTypes: List<SearchType>? = null,
    val suggestionPayload: Payload? = null,
    val suggestionStringPath: Pair<String, String>? = null,
    val suggestionJsonBetween: Pair<String, String>? = null,
    val trendingList: List<String> = emptyList(),
    val feedFetchInterval: Int = 0
): Info