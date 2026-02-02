package org.eve.domain.tasks.exceptions

import org.eve.utils.entities.DefaultError

val TASK_NOT_FOUND = DefaultError("TASK_NOT_FOUND", "Task not found")
val TASK_TITLE_IS_EMPTY = DefaultError("TASK_TITLE_IS_EMPTY", "Task title is empty")
val TASK_DESCRIPTION_IS_EMPTY = DefaultError("TASK_DESCRIPTION_IS_EMPTY", "Task description is empty")
val TASK_PLATFORM_IS_EMPTY = DefaultError("TASK_PLATFORM_IS_EMPTY", "Task platform is empty")