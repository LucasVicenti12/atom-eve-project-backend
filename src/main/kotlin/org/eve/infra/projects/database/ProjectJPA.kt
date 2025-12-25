package org.eve.infra.projects.database

import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.eve.domain.projects.entities.Project
import org.eve.infra.platforms.database.PlatformJPA
import org.eve.utils.jpa.EveBaseJPA
import java.util.UUID

@Entity
@Table(name = "projects")
class ProjectJPA : PanacheEntityBase() {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.UUID)
    @field:Column(name = "uuid", nullable = false)
    var uuid: UUID? = null

    @field:Column(name = "title", nullable = false, length = 70)
    var title: String? = null

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

    fun toProject(): Project = Project(
        uuid = this.uuid,
        title = this.title!!,
        description = this.description!!,
        color = this.color!!,
    )
}

@ApplicationScoped
class ProjectRepositoryJPA : EveBaseJPA<ProjectJPA, UUID>() {
    fun updateProject(project: Project) {
        this.update(
            "title = :title, description = :description, color = :color WHERE uuid = :uuid",
            mapOf(
                "title" to project.title,
                "description" to project.description,
                "color" to project.color,
                "uuid" to project.uuid
            )
        )
    }
}