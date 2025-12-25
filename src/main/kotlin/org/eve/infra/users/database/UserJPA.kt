package org.eve.infra.users.database

import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.*
import org.eve.domain.users.entities.User
import org.eve.domain.users.entities.UserType
import org.eve.utils.jpa.EveBaseJPA
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

    @get:Enumerated(EnumType.ORDINAL)
    @get:Column(name = "user_type", nullable = false)
    var userType: UserType? = null

    @get:Column(name = "name", nullable = false)
    var name: String? = null

    @get:Column("email", nullable = false)
    var email: String? = null

    fun toUserWithPassword(): User = User(
        uuid = this.uuid,
        username = this.username!!,
        password = this.password,
        userType = this.userType!!,
        name = this.name!!,
        email = this.email!!,
    )

    fun toUserWithoutPassword(): User = User(
        uuid = this.uuid,
        username = this.username!!,
        password = null,
        userType = this.userType!!,
        name = this.name!!,
        email = this.email!!,
    )
}

@ApplicationScoped
class UserRepositoryJPA : EveBaseJPA<UserJPA, UUID>() {
    fun updateUser(user: User) {
        this.update(
            "user_type = :userType, name = :name, email = :email WHERE uuid = :uuid",
            mapOf(
                "userType" to user.userType,
                "name" to user.name,
                "email" to user.email,
                "uuid" to user.uuid
            )
        )
    }

    fun findByUsername(username: String): UserJPA? = find(
        "username",
        username
    ).firstResult()
}