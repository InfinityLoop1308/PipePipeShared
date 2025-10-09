package project.pipepipe.shared.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import project.pipepipe.shared.uistate.BaseUiState

abstract class BaseViewModel<S : BaseUiState>(initialState: S) {
    
    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    protected fun setState(reducer: (S) -> S) {
        _uiState.update(reducer)
    }
}
