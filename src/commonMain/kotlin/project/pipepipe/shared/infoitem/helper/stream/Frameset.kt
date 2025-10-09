package project.pipepipe.shared.infoitem.helper.stream

import kotlinx.serialization.Serializable

@Serializable

class Frameset(
    val urls: List<String>,
    val frameWidth: Int,
    val frameHeight: Int,
    val totalCount: Int,
    val durationPerFrame: Int,
    val framesPerPageX: Int,
    val framesPerPageY: Int
)  {

    /**
     * Returns the information for the frame at stream position.
     *
     * @param position Position in milliseconds
     * @return An `IntArray` containing the bounds and URL where the indexes are:
     * - 0: Index of the URL
     * - 1: Left bound
     * - 2: Top bound
     * - 3: Right bound
     * - 4: Bottom bound
     */
    fun getFrameBoundsAt(position: Long): IntArray {
        if (position < 0 || position > ((totalCount + 1).toLong() * durationPerFrame)) {
            // Return the first frame as fallback
            return intArrayOf(0, 0, 0, frameWidth, frameHeight)
        }

        val framesPerStoryboard = framesPerPageX * framesPerPageY
        val absoluteFrameNumber = minOf((position / durationPerFrame).toInt(), totalCount)
        val relativeFrameNumber = absoluteFrameNumber % framesPerStoryboard

        val rowIndex = relativeFrameNumber / framesPerPageX
        val columnIndex = relativeFrameNumber % framesPerPageY

        return intArrayOf(
            absoluteFrameNumber / framesPerStoryboard,  // storyboardIndex
            columnIndex * frameWidth,                  // left
            rowIndex * frameHeight,                    // top
            columnIndex * frameWidth + frameWidth,     // right
            rowIndex * frameHeight + frameHeight       // bottom
        )
    }
}
