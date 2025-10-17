package project.pipepipe.shared.infoitem

import kotlinx.serialization.Serializable

@Serializable
data class PlaylistInfo(
    val url: String,
    val name: String,
    val serviceId: String? = null,
    var thumbnailUrl: String? = null,
    val uploaderName: String? = null,
    var uploaderUrl: String? = null,
    var uploaderAvatarUrl: String? = null,
    var streamCount: Long = 0,
    // Local properties
    var isPinned: Boolean = false,
    var uid: Long? = null
): Info