package project.pipepipe.shared.infoitem.helper.stream

import kotlinx.serialization.Serializable

@Serializable
data class VideoStream(
    val id: String,
    var url: String,
    val mimeType: String,
    val codec: String,
    val bandwidth: Long,
    val width: Int,
    val height: Int,
    val frameRate: String,
    val indexRange: String,
    val initialization: String,
    val isHdr: Boolean = false
)

@Serializable
data class AudioStream(
    val id: String,
    var url: String,
    val mimeType: String,
    val codec: String,
    val bandwidth: Long,
    val indexRange: String,
    val initialization: String,
    val samplingRate: String? = null,
    val language: String? = null,
    val isDefault: Boolean = false
)

@Serializable
data class SubtitleStream(
    val id: String,
    val mimeType: String,
    val url: String,
    val language: String
)