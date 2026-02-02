package org.eve.utils.functions

fun String.SanitizeHTML(): String {
    val pattern = Regex("</?!?(img|a)[^>]*>")
    val match = pattern.find(this)

    return if (match != null) {
        pattern.replaceFirst(this, "")
    } else {
        this
    }
}