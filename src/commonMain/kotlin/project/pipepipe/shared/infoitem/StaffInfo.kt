package project.pipepipe.shared.infoitem

import kotlinx.serialization.Serializable

@Serializable
data class StaffInfo(
    val url: String,
    val name: String,
    val thumbnailUrl: String?,
    val title: String
): Info