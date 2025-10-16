package project.pipepipe.shared.infoitem

import kotlinx.serialization.Serializable
@Serializable
data class ChannelInfo(
    val url: String,
    val name: String,
    val serviceId: String,
    var thumbnailUrl: String? = null,
    var bannerUrl: String? = null,
    var description: String? = null,
    var subscriberCount: Long? = null,
    var tabs: List<ChannelTabInfo> = emptyList()
) : Info
