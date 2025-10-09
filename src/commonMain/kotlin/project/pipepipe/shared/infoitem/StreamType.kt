package project.pipepipe.shared.infoitem

import kotlinx.serialization.Serializable

@Serializable
enum class StreamType {
    NONE,
    VIDEO_STREAM,
    AUDIO_STREAM,
    LIVE_STREAM,
    AUDIO_LIVE_STREAM,
    POST_LIVE_STREAM, //TODO
    POST_LIVE_AUDIO_STREAM
}