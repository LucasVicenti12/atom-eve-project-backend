package org.eve.infra.projects.database

import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.eve.domain.projects.entities.Member
import org.eve.infra.users.database.UserJPA
import org.eve.utils.entities.EveBaseJPA
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "project_members")
class ProjectMembers : PanacheEntityBase() {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.UUID)
    @field:Column(name = "uuid", nullable = false)
    var uuid: UUID? = null

    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "user_uuid", nullable = false)
    var user: UserJPA? = null

    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "project_uuid", nullable = false)
    var project: ProjectJPA? = null

    @field:Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    fun toMember(): Member = Member(
        user = this.user!!.toUserWithoutPassword(),
        createdAt = this.createdAt
    )
}

@ApplicationScoped
class ProjectMembersRepositoryJPA : EveBaseJPA<ProjectMembers, UUID>() {
    fun removeByUserUUID(projectUUID: UUID, userUUID: UUID) {
        this.delete(
            "project.uuid = :projectUUID AND user.uuid = :userUUID",
            mapOf(
                "projectUUID" to projectUUID,
                "userUUID" to userUUID
            )
        )
    }
}