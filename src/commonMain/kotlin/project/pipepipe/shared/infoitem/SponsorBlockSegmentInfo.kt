package project.pipepipe.shared.infoitem

import kotlinx.serialization.Serializable
import project.pipepipe.shared.infoitem.helper.SponsorBlockCategory

@Serializable
data class SponsorBlockSegmentInfo(
    val uuid: String,
    var startTime: Double,  // in milliseconds
    var endTime: Double,    // in milliseconds
    val category: SponsorBlockCategory,
    var hasVoted: Boolean = false
): Info