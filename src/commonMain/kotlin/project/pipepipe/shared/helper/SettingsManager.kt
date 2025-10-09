package project.pipepipe.shared.helper

import com.russhwolf.settings.Settings
import kotlinx.serialization.json.Json

class SettingsManager(private val settings: Settings = Settings()) {


    companion object {
        private const val LEGACY_SET_DELIMITER = "\u001F"
        private val json = Json { encodeDefaults = true }
    }


    fun getString(key: String, defaultValue: String = ""): String {
        return settings.getString(key, defaultValue)
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return settings.getInt(key, defaultValue)
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return settings.getBoolean(key, defaultValue)
    }

    fun getFloat(key: String, defaultValue: Float = 0f): Float {
        return settings.getFloat(key, defaultValue)
    }

    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return settings.getLong(key, defaultValue)
    }

    fun getDouble(key: String, defaultValue: Double = 0.0): Double {
        return settings.getDouble(key, defaultValue)
    }

    fun putString(key: String, value: String) {
        settings.putString(key, value)
    }

    fun putInt(key: String, value: Int) {
        settings.putInt(key, value)
    }

    fun putBoolean(key: String, value: Boolean) {
        settings.putBoolean(key, value)
    }

    fun putFloat(key: String, value: Float) {
        settings.putFloat(key, value)
    }

    fun putLong(key: String, value: Long) {
        settings.putLong(key, value)
    }

    fun putDouble(key: String, value: Double) {
        settings.putDouble(key, value)
    }

    fun getStringSet(key: String, defaultValues: Set<String> = emptySet()): Set<String> {
        val stored = settings.getStringOrNull(key) ?: return defaultValues
        return decodeStringSet(stored) ?: defaultValues
    }

    fun putStringSet(key: String, values: Set<String>) {
        val encoded = json.encodeToString(values.toList())
        settings.putString(key, encoded)
    }


    fun hasKey(key: String): Boolean {
        return settings.hasKey(key)
    }

    fun remove(key: String) {
        settings.remove(key)
    }

    fun clear() {
        settings.clear()
    }

    fun snapshot(): Map<String, Any?> {
        val snapshot = LinkedHashMap<String, Any?>()
        for (key in settings.keys) {
            readValueForKey(key)?.let { snapshot[key] = it }
        }
        return snapshot
    }

    fun restoreFrom(data: Map<String, Any?>, clearBefore: Boolean = true) {
        if (clearBefore) {
            clear()
        }

        data.forEach { (key, value) ->
            when (value) {
                null -> remove(key)
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is Double -> putDouble(key, value)
                is String -> putString(key, value)
                is Iterable<*> -> {
                    if (value is Collection<*> && value.all { it is String }) {
                        putStringSet(key, value.filterIsInstance<String>().toSet())
                    } else {
                        putString(key, value.toString())
                    }
                }
                is Number -> putDouble(key, value.toDouble())
                else -> putString(key, value.toString())
            }
        }
    }

    private fun readValueForKey(key: String): Any? {
        safeGet { settings.getBooleanOrNull(key) }?.let { return it }
        safeGet { settings.getIntOrNull(key) }?.let { return it }
        safeGet { settings.getLongOrNull(key) }?.let { return it }
        safeGet { settings.getFloatOrNull(key) }?.let { return it }
        safeGet { settings.getDoubleOrNull(key) }?.let { return it }
        val stringValue = safeGet { settings.getStringOrNull(key) } ?: return null
        return decodeStringSet(stringValue) ?: stringValue
    }

    private fun decodeStringSet(value: String): Set<String>? {
        if (value.isEmpty()) return emptySet()
        safeGet { json.decodeFromString<List<String>>(value) }?.let { return it.toSet() }
        if (value.contains(LEGACY_SET_DELIMITER)) {
            return value.split(LEGACY_SET_DELIMITER)
                .filter { it.isNotEmpty() }
                .toSet()
        }
        return null
    }


    private inline fun <T> safeGet(block: () -> T?): T? =
        try {
            block()
        } catch (_: Throwable) {
            null
        }
}