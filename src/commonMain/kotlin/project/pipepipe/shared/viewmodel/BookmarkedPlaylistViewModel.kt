package project.pipepipe.shared.viewmodel

import project.pipepipe.shared.database.DatabaseOperations
import project.pipepipe.shared.uistate.BookmarkedPlaylistUiState

class BookmarkedPlaylistViewModel : BaseViewModel<BookmarkedPlaylistUiState>(BookmarkedPlaylistUiState()) {

    suspend fun loadPlaylists() {
        setState {
            it.copy(
                common = it.common.copy(isLoading = true)
            )
        }
        val playlists = DatabaseOperations.getAllPlaylistsCombined()
        setState {
            it.copy(
                common = it.common.copy(isLoading = false),
                playlists = playlists
            )
        }
    }
    fun reorderItems(fromIndex: Int, toIndex: Int){
        val items = uiState.value.playlists.toMutableList()
        val item = items.removeAt(fromIndex)
        items.add(toIndex,item)
        setState {
            it.copy(
                playlists = items
            )
        }
    }
}
