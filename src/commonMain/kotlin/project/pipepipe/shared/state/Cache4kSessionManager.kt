package project.pipepipe.shared.state

import io.github.reactivecircus.cache4k.Cache
import kotlin.time.Duration.Companion.days

class Cache4kSessionManager : SessionManager {

    companion object {
        private const val SESSION_KEY_PREFIX = "session:"
        private val TTL_DURATION = 1.days
    }

    private val cache: Cache<String, State> = Cache.Builder<String, State>()
        .expireAfterWrite(TTL_DURATION)
        .maximumCacheSize(1000)
        .build()

    override suspend fun saveState(sessionId: String, state: State) {
        val key = "$SESSION_KEY_PREFIX$sessionId"
        try {
            cache.put(key, state)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun loadState(sessionId: String): State? {
        val key = "$SESSION_KEY_PREFIX$sessionId"
        return try {
            cache.get(key)
        } catch (e: Exception) {
            throw e
        }
    }

    override fun removeState(sessionId: String) {
        cache.invalidate("$SESSION_KEY_PREFIX$sessionId")
    }

    fun clearAllStates() {
        cache.invalidateAll()
    }

    fun getCacheStats() = cache.asMap().size
}