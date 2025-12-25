package project.pipepipe.shared.infoitem

import kotlinx.serialization.Serializable

@Serializable
sealed interface Info

val Info.url: String
    get() = when (this) {
        is StreamInfo -> url
        is ChannelInfo -> url
        is PlaylistInfo -> url
        else -> error("Info doesn't have url")
    }

val Info.serviceId: Int?
    get() = when (this) {
        is StreamInfo -> serviceId
        is ChannelInfo -> serviceId
        is PlaylistInfo -> serviceId
        else ->  error("Info doesn't have serviceId")
    }
