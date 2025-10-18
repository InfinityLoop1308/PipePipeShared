package project.pipepipe.shared.infoitem

import kotlinx.serialization.Serializable

@Serializable
data class TrendingInfo(val url: String, val serviceId: String, val name: String): Info
