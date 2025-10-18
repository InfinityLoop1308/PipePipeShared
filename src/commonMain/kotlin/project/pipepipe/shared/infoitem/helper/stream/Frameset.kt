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
)