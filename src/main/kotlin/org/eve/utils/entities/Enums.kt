package org.eve.utils.entities

interface HasType {
    val type: String
}

interface HasCode {
    val code: Int
}

inline fun <reified T : Enum<T>, K> enumConvert(value: K): T {
    return enumValues<T>().firstOrNull {
        (it as? HasType)?.type?.equals(value as String, ignoreCase = true) == true ||
                (it as? HasCode)?.code === value
    } ?: throw IllegalArgumentException("Invalid enum type: $value")
}