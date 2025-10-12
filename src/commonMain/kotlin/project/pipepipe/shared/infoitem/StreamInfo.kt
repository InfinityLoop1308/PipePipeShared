package project.pipepipe.shared.infoitem

//import project.pipepipe.shared.infoitem.helper.stream.SubtitlesStream
import kotlinx.serialization.Serializable
import project.pipepipe.shared.infoitem.helper.stream.*

@Serializable
data class StreamInfo(
    val url: String,
    val serviceId: String,
    var id: String? = null,
    var name: String? = null,
    var streamType: StreamType = StreamType.NONE,
    var uploaderName: String? = null,
    var uploaderUrl: String? = null,
    var uploaderAvatarUrl: String? = null,
    var uploadDate: Long? = null, // timestamp in milliseconds
    var duration: Long? = null, // second
    var viewCount: Long? = null,
    var likeCount: Long? = null,
    var dislikeCount: Long? = null,
    var uploaderSubscriberCount: Long? = null,
    var staffs: Collection<StaffInfo>? = null,
    var dashUrl: String? = null,
    var dashManifest: String?= null,
    var hlsUrl: String? = null,
    var startPosition: Long? = null,
    var streamSegments: List<StreamSegment>? = null,
//    var metaInfo: List<MetaInfotmp>? = null,
    var previewFrames: List<Frameset>? = null,
    var isRoundPlayStream: Boolean = false,
    var isPortrait: Boolean = false,
    var startAt: Long? = null,
    var shortFormContent: Boolean = false,
    var thumbnailUrl: String? = null,
    var description: Description? = null,
    var tags: List<String>? = null,
    var stats: Map<String, String>? = null,
    var isPaid: Boolean = false,
    var commentInfo: CommentInfo? = null,
    var danmakuUrl: String? = null,
    var relatedItemInfo: RelatedItemInfo? = null,
    var sponsorblockUrl: String? = null,
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