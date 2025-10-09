package project.pipepipe.shared.infoitem.helper.stream

import kotlinx.serialization.Serializable

@Serializable
data class Description(val content: String?, val type: Int) {
    companion object {
        const val HTML: Int = 1
        const val MARKDOWN: Int = 2
        const val PLAIN_TEXT: Int = 3
        var EMPTY_DESCRIPTION: Description = Description(null, PLAIN_TEXT)
    }
}