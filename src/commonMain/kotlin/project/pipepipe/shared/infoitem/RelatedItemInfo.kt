package project.pipepipe.shared.infoitem

import kotlinx.serialization.Serializable

@Serializable
data class RelatedItemInfo(val url: String, val partitions: List<StreamInfo>? = null): Info