package project.pipepipe.shared.job

import kotlinx.serialization.Serializable
import project.pipepipe.shared.infoitem.Info

@Serializable
data class PagedData<I : Info>(val itemList: List<I>, val nextPageUrl: String?)
@Serializable
data class ErrorDetail(
    val code: String,
    val stackTrace: String,
    val errorId: Long? = null
)
@Serializable
data class ExtractResult<META : Info, DATA: Info>(
    val info: META? = null,
    val errors: List<String> = emptyList(),
    val pagedData: PagedData<DATA>? = null,
    val fatalError: ErrorDetail? = null,
)