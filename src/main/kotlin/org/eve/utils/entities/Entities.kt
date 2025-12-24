package org.eve.utils.entities

val UNEXPECTED_ERROR = DefaultError("UNEXPECTED_ERROR", "An unexpected error has occurred")

interface HasType {
    val type: String
}

interface HasCode {
    val code: Int
}

data class Pagination<T>(
    val items: List<T>,
    val totalPages: Int,
    val totalCount: Long,
    val page: Int,
    val count: Int,
)

data class DefaultError(
    val code: String,
    val message: String,
)

data class DefaultResponse<T>(
    val data: T? = null,
    val error: DefaultError? = null
)