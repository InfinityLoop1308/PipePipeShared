package project.pipepipe.shared.infoitem.helper
import kotlinx.serialization.Serializable

@Serializable
data class SearchFilterGroup(
    val groupName: String,
    val onlyOneCheckable: Boolean = false,
    val availableSearchFilterItems: List<SearchFilterItem>,
    val defaultFilter: SearchFilterItem? = null,
    val selectedSearchFilters: List<SearchFilterItem> = emptyList()
) {
    init {
        require(groupName.isNotBlank()) { "Group name cannot be blank" }
        require(availableSearchFilterItems.isNotEmpty()) { "Filter group must contain at least one filter item" }
    }
}

@Serializable
data class SearchFilterItem(
    val name: String,
    val parameter: String,
)

@Serializable
data class SearchType(
    val name: String,
    val baseUrl: String,
    val availableSearchFilterGroups: List<SearchFilterGroup>? = null
)

