package project.pipepipe.shared.infoitem

import kotlinx.serialization.Serializable
@Serializable
enum class ChannelTabType {
    VIDEOS,
    TRACKS,
    SHORTS,
    LIVE,
    CHANNELS,
    PLAYLISTS,
    ALBUMS,
    INFO
}
@Serializable
data class ChannelTabInfo(val url: String, val type: ChannelTabType)