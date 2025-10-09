package project.pipepipe.shared.downloader

import project.pipepipe.shared.infoitem.CookieInfo

interface CookieManager {
    fun getCookie(id: String): String?
    fun getCookieInfo(id: String): CookieInfo?
    fun isCookieExpired(id: String): Boolean
    fun setCookieInfo(id: String, cookieInfo: CookieInfo): Boolean
}