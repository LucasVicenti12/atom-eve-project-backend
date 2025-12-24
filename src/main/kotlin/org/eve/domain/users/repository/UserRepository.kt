package org.eve.domain.users.repository

import org.eve.domain.users.entities.User
import org.eve.utils.entities.Pagination
import java.util.UUID

interface UserRepository {
    fun createUser(user: User): User?
    fun updateUser(user: User): User?

    fun getUserByUUID(uuid: UUID): User?
    fun getUserByUserName(username: String): User?
    fun getPaginatedUsers(page: Int, count: Int): Pagination<User>
}