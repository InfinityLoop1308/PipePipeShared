package project.pipepipe.shared.downloader

import project.pipepipe.shared.infoitem.CookieInfo

interface CookieManager {
    fun getCookie(id: Int): String?
    fun getCookieInfo(id: Int): CookieInfo?
    fun isCookieExpired(id: Int): Boolean
    fun setCookieInfo(id: Int, cookieInfo: CookieInfo, isLoggedInCookie: Boolean)
    fun removeLoggedInCookie(id: Int)
    fun setCookie(id: Int, cookies: String, timeout: Long, isLoggedInCookie: Boolean)
}

fun String.isLoggedInCookie(): Boolean = this.contains("is_logged_in=1")