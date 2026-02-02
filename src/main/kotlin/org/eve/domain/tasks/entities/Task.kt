package org.eve.domain.tasks.entities

import org.eve.domain.platforms.entities.Platform
import org.eve.domain.projects.entities.Project
import java.util.UUID

class Task(
    var uuid: UUID? = null,
    var title: String? = "",
    var description: String? = "",
    var project: Project? = null,
    var platform: Platform? = null,
)

// todo: use this in SprintTask 0ne by One
//enum class TaskStatus(override val code: Int) : HasCode {
//    PENDING(0),
//    DOING(1),
//    DONE(2),
//    REPROVED(3),
//    CANCELED(4),
//    DONE_WITH_ISSUES(5)
//}