package project.pipepipe.shared.infoitem.helper.stream

import kotlinx.serialization.Serializable

@Serializable
data class StreamSegment(
    var title: String,
    var startTimeSeconds: Int
)  {
    var channelName: String? = null
    var url: String? = null
    var previewUrl: String? = null
}
