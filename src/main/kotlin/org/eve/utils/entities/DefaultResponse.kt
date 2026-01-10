package org.eve.utils.entities

data class DefaultResponse<T>(
    val data: T? = null,
    val error: DefaultError? = null
)