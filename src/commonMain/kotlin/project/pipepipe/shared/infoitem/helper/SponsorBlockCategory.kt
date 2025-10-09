package project.pipepipe.shared.infoitem.helper

import kotlinx.serialization.Serializable

@Serializable
enum class SponsorBlockCategory(val apiName: String) {
    SPONSOR("sponsor"),
    INTRO("intro"),
    OUTRO("outro"),
    INTERACTION("interaction"),
    HIGHLIGHT("poi_highlight"),
    SELF_PROMO("selfpromo"),
    NON_MUSIC("music_offtopic"),
    PREVIEW("preview"),
    FILLER("filler"),
    PENDING("pending");

    companion object {
        fun fromApiName(apiName: String): SponsorBlockCategory = when (apiName) {
            "sponsor" -> SPONSOR
            "intro" -> INTRO
            "outro" -> OUTRO
            "interaction" -> INTERACTION
            "poi_highlight" -> HIGHLIGHT
            "selfpromo" -> SELF_PROMO
            "music_offtopic" -> NON_MUSIC
            "preview" -> PREVIEW
            "filler" -> FILLER
            else -> throw IllegalArgumentException("Invalid API name")
        }
    }
}

