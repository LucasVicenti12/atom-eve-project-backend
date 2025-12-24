package org.eve.domain.users.usecase

import org.eve.domain.users.entities.User
import org.eve.utils.entities.DefaultResponse
import org.eve.utils.entities.Pagination
import java.util.UUID

interface UserUseCase {
    fun createUser(user: User): DefaultResponse<User>
    fun updateUser(user: User): DefaultResponse<User>

    fun getUserByUUID(uuid: UUID): DefaultResponse<User>
    fun getUserByUserName(username: String): DefaultResponse<User>
    fun getPaginatedUsers(page: Int, count: Int): DefaultResponse<Pagination<User>>
}