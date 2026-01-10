package org.eve.infra.users.repository

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.eve.domain.users.entities.User
import org.eve.domain.users.repository.UserRepository
import org.eve.infra.users.database.UserJPA
import org.eve.infra.users.database.UserRepositoryJPA
import org.eve.utils.entities.Pagination
import java.util.*


@ApplicationScoped
class UserRepositoryImplementation(
    private val userRepositoryJPA: UserRepositoryJPA,
) : UserRepository {

    @Transactional
    override fun createUser(user: User): User? {
        val userJPA = UserJPA().apply {
            this.username = user.username
            this.password = user.password
            this.status = user.status
            this.name = user.name
            this.email = user.email
        }

        userRepositoryJPA.persist(userJPA)

        return userJPA.toUserWithoutPassword()
    }

    @Transactional
    override fun updateUser(user: User): User? {
        userRepositoryJPA.updateUser(user)

        return userRepositoryJPA.findById(user.uuid!!)?.toUserWithoutPassword()
    }

    @Transactional
    override fun getUserByUUID(uuid: UUID): User? =
        userRepositoryJPA.findById(uuid)?.toUserWithoutPassword()

    @Transactional
    override fun getUserByUserName(username: String): User? =
        userRepositoryJPA.findByUsername(username)?.toUserWithPassword()

    @Transactional
    override fun getPaginatedUsers(
        page: Int,
        count: Int
    ): Pagination<User> {
        val paginated = userRepositoryJPA.findPaginated(page, count)

        val items = paginated.list<UserJPA>()
            .map {
                it.toUserWithoutPassword()
            }

        return Pagination(
            items = items,
            totalPages = paginated.pageCount(),
            totalCount = paginated.count(),
            page = page,
            count = count
        )
    }
}