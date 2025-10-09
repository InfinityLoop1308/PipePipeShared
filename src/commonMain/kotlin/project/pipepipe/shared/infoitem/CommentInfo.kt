package project.pipepipe.shared.infoitem

import kotlinx.serialization.Serializable

@Serializable
data class CommentInfo(
    val url: String?,
    val content: String?,
    val authorName: String?,
    val authorAvatarUrl: String?,
    val authorUrl: String?,
    val authorVerified: Boolean?,
    val uploadDate: Long?, // timestamp in milliseconds
    val likeCount: Int?,
    val isHeartedByUploader: Boolean?,
    val isPinned: Boolean?,
    val replyCount: Int?,
    val replyInfo: CommentInfo?,
    val images: List<String>?,
    val serviceId: String?
) : Info {
    override fun toString(): String {
        return "$authorName: $content"
    }

    class Builder {
        private var url: String? = null
        private var content: String? = null
        private var authorName: String? = null
        private var authorAvatarUrl: String? = null
        private var authorUrl: String? = null
        private var authorVerified: Boolean? = null
        private var uploadDate: Long? = null
        private var likeCount: Int? = null
        private var isHeartedByUploader: Boolean? = null
        private var isPinned: Boolean? = null
        private var replyCount: Int? = null
        private var replyInfo: CommentInfo? = null
        private var images: List<String>? = null
        private var serviceId: String?= null

        fun url(url: String?) = apply { this.url = url }
        fun content(content: String?) = apply { this.content = content }
        fun likeCount(likeCount: Int?) = apply { this.likeCount = likeCount }
        fun serviceId(serviceId: String) = apply { this.serviceId = serviceId }

        fun build() = CommentInfo(
            url = url,
            content = content,
            authorName = authorName,
            authorAvatarUrl = authorAvatarUrl,
            authorUrl = authorUrl,
            authorVerified = authorVerified,
            uploadDate = uploadDate,
            likeCount = likeCount,
            isHeartedByUploader = isHeartedByUploader,
            isPinned = isPinned,
            replyCount = replyCount,
            replyInfo = replyInfo,
            images = images,
            serviceId = serviceId
        )
    }

    companion object {
        fun builder() = Builder()
    }
}
