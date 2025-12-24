package org.eve.utils.functions

import org.eve.utils.entities.HasCode
import org.eve.utils.entities.HasType


inline fun <reified T : Enum<T>, K> enumConvert(value: K): T {
    return enumValues<T>().firstOrNull {
        (it as? HasType)?.type?.equals(value as String, ignoreCase = true) == true ||
        (it as? HasCode)?.code === value
    } ?: throw IllegalArgumentException("Invalid enum type: $value")
}