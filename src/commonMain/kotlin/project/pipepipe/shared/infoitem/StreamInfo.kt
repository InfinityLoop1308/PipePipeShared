package project.pipepipe.shared.infoitem

import kotlinx.serialization.Serializable
import project.pipepipe.shared.infoitem.helper.stream.*

@Serializable
data class StreamInfo(
    val url: String,
    val serviceId: String,
    var name: String? = null,

    var streamType: StreamType = StreamType.NONE,
    var uploaderName: String? = null,
    var uploaderUrl: String? = null,
    var uploaderAvatarUrl: String? = null,
    var uploadDate: Long? = null, // timestamp in milliseconds
    var duration: Long? = null, // second
    var viewCount: Long? = null,
    var isShort: Boolean = false,
    var thumbnailUrl: String? = null,
    var isPaid: Boolean = false,

    // details page only.
    // 1. play info
    var dashUrl: String? = null,
    var dashManifest: String?= null,
    var hlsUrl: String? = null,
    var initialTimestamp: Long? = null,
    var streamSegments: List<StreamSegment>? = null,
    var previewFrames: List<Frameset>? = null,
    // 1.1 live info
    var startAt: Long? = null,
    // 2. details
    var likeCount: Long? = null,
    var uploaderSubscriberCount: Long? = null,
    var isPortrait: Boolean = false,
    var commentUrl: String? = null,
    var danmakuUrl: String? = null,
    var relatedItemUrl: String? = null,
    var sponsorblockUrl: String? = null,
    var description: Description? = null,
    var tags: List<String>? = null,
    var staffs: Collection<StaffInfo>? = null,
    // local properties
    val progress: Long? = null,
    val joinId: Long? = null,
    val localLastViewDate: Long? = null,
    val localRepeatCount: Long? = null,
    var headers: HashMap<String, String> = hashMapOf()
) : Info {

    override fun toString(): String =
        "<$uploaderName> $name (${url})"
}


data class StreamInfoWithCallback(
    val streamInfo: StreamInfo,
    val onNavigateTo: (() -> Unit)? = null,
    val onDelete: (() -> Unit)? = null,
): Info