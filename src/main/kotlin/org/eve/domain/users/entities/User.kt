package org.eve.domain.users.entities

import org.eve.utils.entities.HasCode
import java.time.LocalDateTime
import java.util.UUID

class User(
    var uuid: UUID? = null,
    var username: String? = "",
    var password: String? = null,
    var name: String? = "",
    var email: String? = "",
    var status: UserStatus? = UserStatus.ACTIVE,
    var createdAt: LocalDateTime? = null,
    var modifiedAt: LocalDateTime? = null
)

enum class UserStatus(override val code: Int) : HasCode {
    ACTIVE(0),
    INACTIVE(1)
}