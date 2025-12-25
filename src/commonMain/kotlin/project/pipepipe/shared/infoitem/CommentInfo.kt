package project.pipepipe.shared.infoitem

import kotlinx.serialization.Serializable

@Serializable
data class CommentInfo(
    val url: String?,
    val serviceId: Int?,
    var content: String? = null,
    var authorName: String? = null,
    var authorAvatarUrl: String? = null,
    var authorUrl: String? = null,
    var authorVerified: Boolean? = null,
    var uploadDate: Long? = null, // timestamp in milliseconds
    var likeCount: Int? = null,
    var isHeartedByUploader: Boolean? = null,
    var isPinned: Boolean? = null,
    var replyCount: Int? = null,
    var replyInfo: CommentInfo? = null,
    var images: List<String>? = null,
) : Info {
    override fun toString(): String {
        return "$authorName: $content"
    }
}
