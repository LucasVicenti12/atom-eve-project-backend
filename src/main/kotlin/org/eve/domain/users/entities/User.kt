package org.eve.domain.users.entities

import org.eve.utils.entities.HasCode
import java.util.UUID

class User(
    var uuid: UUID? = null,
    var username: String? = "",
    var password: String? = null,
    var userType: UserType? = UserType.DEVELOPER,
    var name: String? = "",
    var email: String? = ""
)

enum class UserType(override val code: Int) : HasCode {
    DEVELOPER(0),
    ADMIN(1),
}