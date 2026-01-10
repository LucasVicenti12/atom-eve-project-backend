package org.eve.utils.entities

data class Pagination<T>(
    val items: List<T>,
    val totalPages: Int,
    val totalCount: Long,
    val page: Int,
    val count: Int,
)