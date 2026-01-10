package org.eve.infra.users.database

import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.*
import org.eve.domain.users.entities.User
import org.eve.domain.users.entities.UserStatus
import org.eve.utils.entities.EveBaseJPA
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "users")
class UserJPA : PanacheEntityBase() {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.UUID)
    @get:Column(name = "uuid", nullable = false)
    var uuid: UUID? = null

    @get:Column(name = "username", nullable = false)
    var username: String? = null

    @get:Column("password", nullable = false)
    var password: String? = null

    @get:Column(name = "name", nullable = false)
    var name: String? = null

    @get:Column("email", nullable = false)
    var email: String? = null

    @get:Enumerated(EnumType.ORDINAL)
    @get:Column(name = "status", nullable = false)
    var status: UserStatus? = null

    @get:Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @get:Column(name = "modified_at", nullable = false)
    var modifiedAt: LocalDateTime = LocalDateTime.now()

    fun toUserWithPassword(): User = User(
        uuid = this.uuid,
        username = this.username!!,
        password = this.password!!,
        name = this.name!!,
        email = this.email!!,
        status = this.status,
        createdAt = this.createdAt,
        modifiedAt = this.modifiedAt
    )

    fun toUserWithoutPassword(): User = User(
        uuid = this.uuid,
        username = this.username!!,
        password = null,
        name = this.name!!,
        email = this.email!!,
        status = this.status,
        createdAt = this.createdAt,
        modifiedAt = this.modifiedAt
    )
}

@ApplicationScoped
class UserRepositoryJPA : EveBaseJPA<UserJPA, UUID>() {
    fun updateUser(user: User) {
        this.update(
            "name = :name, email = :email, status = :status WHERE uuid = :uuid",
            mapOf(
                "name" to user.name,
                "email" to user.email,
                "status" to user.status,
                "uuid" to user.uuid
            )
        )
    }

    fun findByUsername(username: String): UserJPA? = find(
        "username",
        username
    ).firstResult()
}