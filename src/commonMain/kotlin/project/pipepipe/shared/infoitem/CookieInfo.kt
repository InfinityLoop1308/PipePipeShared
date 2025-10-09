package project.pipepipe.shared.infoitem

import kotlinx.serialization.Serializable

@Serializable
data class CookieInfo(val cookie: String?, val timeOut: Long): Info