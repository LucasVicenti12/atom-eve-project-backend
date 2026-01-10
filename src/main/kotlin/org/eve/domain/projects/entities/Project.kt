package org.eve.domain.projects.entities

import org.eve.domain.platforms.entities.Platform
import org.eve.domain.users.entities.User
import org.eve.utils.entities.HasCode
import java.time.LocalDateTime
import java.util.UUID

class Project(
    var uuid: UUID? = null,
    var name: String? = "",
    var description: String? = "",
    var color: String? = "",
    var platforms: List<Platform>? = null,
    var owner: User? = null,
    var members: List<Member>? = null,
    var status: ProjectStatus? = ProjectStatus.ACTIVE,
    var createdAt: LocalDateTime? = null,
    var modifiedAt: LocalDateTime? = null
)

class Member(
    var user: User,
    var createdAt: LocalDateTime? = null,
)

enum class ProjectStatus(override val code: Int) : HasCode {
    ACTIVE(0),
    INACTIVE(1)
}