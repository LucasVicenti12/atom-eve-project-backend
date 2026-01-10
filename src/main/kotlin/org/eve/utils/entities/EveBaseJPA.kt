package org.eve.utils.entities

import io.quarkus.hibernate.orm.panache.PanacheQuery
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import io.quarkus.panache.common.Page

abstract class EveBaseJPA<T, ID>: PanacheRepositoryBase<T, ID> {
    fun findPaginated(page: Int, count: Int): PanacheQuery<T> = findAll().page(
        Page(page, count)
    )
}