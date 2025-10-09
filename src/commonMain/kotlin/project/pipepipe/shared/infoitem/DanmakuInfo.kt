package project.pipepipe.shared.infoitem

import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class DanmakuInfo(
    var text: String,
    var timestamp: Duration? = null,
    var argbColor: Int = 0xFFFFFFFF.toInt(),
    var position: Position = Position.REGULAR,
    var relativeFontSize: Double = 0.7,
    var isLive: Boolean = false,
    var lastingTime: Int = 10,
    var isSuperChat: Boolean = false
) : Info {

    enum class Position {
        REGULAR,
        BOTTOM,
        TOP
    }

    fun compareTo(other: DanmakuInfo): Int {
        val thisTime = timestamp
        val otherTime = other.timestamp
        return when {
            thisTime == null && otherTime == null -> 0
            thisTime == null -> -1
            otherTime == null -> 1
            else -> thisTime.compareTo(otherTime)
        }
    }

    override fun toString(): String {
        return "${timestamp?:"Live"} $text"
    }
}
