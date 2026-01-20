package org.eve.infra.projects.database

import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import io.quarkus.hibernate.orm.panache.PanacheQuery
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.eve.domain.projects.entities.Project
import org.eve.domain.projects.entities.ProjectStatus
import org.eve.infra.platforms.database.PlatformJPA
import org.eve.infra.users.database.UserJPA
import org.eve.utils.entities.EveBaseJPA
import java.time.LocalDateTime
import java.util.UUID
import io.quarkus.panache.common.Page

@Entity
@Table(name = "projects")
class ProjectJPA : PanacheEntityBase() {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.UUID)
    @field:Column(name = "uuid", nullable = false)
    var uuid: UUID? = null

    @field:Column(name = "name", nullable = false, length = 70)
    var name: String? = null

    @field:Column(name = "description", nullable = false)
    var description: String? = null

    @field:Column(name = "color", nullable = false, length = 10)
    var color: String? = null

    @field:OneToMany(
        mappedBy = "project",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE]
    )
    var platforms: MutableList<PlatformJPA> = mutableListOf()

    @field:ManyToOne(fetch = FetchType.LAZY)
    var owner: UserJPA? = null

    @field:OneToMany(
        mappedBy = "project",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE]
    )
    var members: MutableList<ProjectMembers> = mutableListOf()

    @field:Enumerated(EnumType.ORDINAL)
    @field:Column(name = "status", nullable = false)
    var status: ProjectStatus? = null

    @field:Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @field:Column(name = "modified_at", nullable = false)
    var modifiedAt: LocalDateTime = LocalDateTime.now()

    fun toProject(): Project = Project(
        uuid = this.uuid,
        name = this.name!!,
        description = this.description!!,
        color = this.color!!,
        platforms = this.platforms.map {
            it.toPlatformWithoutProject()
        },
        members = this.members.map {
            it.toMember()
        },
        owner = this.owner?.toUserWithoutPassword(),
        status = this.status,
        createdAt = this.createdAt,
        modifiedAt = this.modifiedAt
    )

    fun toProjectResume(): Project = Project(
        uuid = this.uuid,
        name = this.name!!,
        description = this.description!!,
        color = this.color!!,
        owner = this.owner?.toUserWithoutPassword(),
        status = this.status,
        createdAt = this.createdAt,
        modifiedAt = this.modifiedAt
    )
}

@ApplicationScoped
class ProjectRepositoryJPA : EveBaseJPA<ProjectJPA, UUID>() {
    fun updateProject(project: Project) {
        this.update(
            "name = :name, description = :description, color = :color WHERE uuid = :uuid",
            mapOf(
                "name" to project.name,
                "description" to project.description,
                "color" to project.color,
                "uuid" to project.uuid
            )
        )
    }

    fun findProjectsByMember(
        userUUID: UUID,
        page: Int,
        count: Int
    ): PanacheQuery<ProjectJPA> {
        return find(
            """
            SELECT DISTINCT p
            FROM ProjectJPA p
            LEFT JOIN p.members m
            WHERE m.user.uuid = :userUUID OR p.owner.uuid = :userUUID
            """.trimIndent(),
            mapOf(
                "userUUID" to userUUID
            )
        ).page(
            Page.of(page, count)
        )
    }

    fun findProjectsByOwner(
        userUUID: UUID,
        page: Int,
        count: Int
    ): PanacheQuery<ProjectJPA> {
        return find(
            """
            SELECT p
            FROM ProjectJPA p
            WHERE p.owner.uuid = :userUUID
            """.trimIndent(),
            mapOf(
                "userUUID" to userUUID
            )
        ).page(
            Page.of(page, count)
        )
    }
}