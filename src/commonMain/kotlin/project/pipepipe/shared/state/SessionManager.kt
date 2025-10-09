package project.pipepipe.shared.state

interface SessionManager {
    suspend fun saveState(sessionId: String, state: State = PlainState(0))
    suspend fun loadState(sessionId: String): State?
    fun removeState(sessionId: String)
}