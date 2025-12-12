package project.pipepipe.shared.downloader

import project.pipepipe.shared.infoitem.CookieInfo

interface CookieManager {
    fun getCookie(id: String): String?
    fun getCookieInfo(id: String): CookieInfo?
    fun isCookieExpired(id: String): Boolean
    fun setCookieInfo(id: String, cookieInfo: CookieInfo, isLoggedInCookie: Boolean)
    fun removeLoggedInCookie(id: String)
    fun setCookie(id: String, cookies: String, timeout: Long, isLoggedInCookie: Boolean)
}

fun String.isLoggedInCookie(): Boolean = this.contains("is_logged_in=1")