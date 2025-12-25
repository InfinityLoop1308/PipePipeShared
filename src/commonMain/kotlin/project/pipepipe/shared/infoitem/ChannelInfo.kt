package project.pipepipe.shared.infoitem

import kotlinx.serialization.Serializable
@Serializable
data class ChannelInfo(
    val url: String,
    val name: String,
    val serviceId: Int,
    var thumbnailUrl: String? = null,
    var description: String? = null,
    var subscriberCount: Long? = null,
    // not used if info only
    var bannerUrl: String? = null,
    var tabs: List<ChannelTabInfo> = emptyList()
) : Info
