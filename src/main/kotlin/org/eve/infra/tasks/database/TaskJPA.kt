package org.eve.infra.tasks.database

import io.quarkus.hibernate.orm.panache.PanacheQuery
import io.quarkus.panache.common.Page
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
import org.eve.domain.tasks.entities.Task
import org.eve.infra.platforms.database.PlatformJPA
import org.eve.infra.projects.database.ProjectJPA
import org.eve.utils.entities.EveBaseJPA
import java.util.UUID

@Entity
@Table(name = "tasks")
class TaskJPA {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.UUID)
    var uuid: UUID? = null

    @field:Column(name = "title", nullable = false, length = 120)
    var title: String? = null

    @field:Column(name = "description", nullable = false)
    var description: String? = null

    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "project_uuid", nullable = false)
    var project: ProjectJPA? = null

    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "platform_uuid", nullable = false)
    var platform: PlatformJPA? = null

    fun toTask(): Task = Task(
        uuid = this.uuid,
        title = this.title!!,
        description = this.description!!,
        platform = this.platform!!.toPlatform(),
        project = this.project!!.toProject()
    )

    fun toTaskWithoutProjectAndPlatform(): Task = Task(
        uuid = this.uuid,
        title = this.title!!,
        description = this.description!!
    )
}

@ApplicationScoped
class TaskRepositoryJPA : EveBaseJPA<TaskJPA, UUID>() {
    fun updateTask(task: Task) {
        this.update(
            "title = :title, description = :description, platform = :platform WHERE uuid = :uuid",
            mapOf(
                "title" to task.title,
                "description" to task.description,
                "platform" to task.platform!!.uuid!!,
                "uuid" to task.uuid
            )
        )
    }

    fun getTaskByProjectUUID(projectUUID: UUID): List<TaskJPA> {
        return this.find(
            "project.uuid = :projectUUID",
            mapOf(
                "projectUUID" to projectUUID
            )
        ).list()
    }

    fun getPaginatedTaskByProjectUUID(
        projectUUID: UUID,
        page: Int,
        count: Int
    ): PanacheQuery<TaskJPA> {
        return this.find(
            "project.uuid = :projectUUID",
            mapOf(
                "projectUUID" to projectUUID
            )
        ).page(
            Page(page, count)
        )
    }
}