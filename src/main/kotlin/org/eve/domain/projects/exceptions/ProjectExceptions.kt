package org.eve.domain.projects.exceptions

import org.eve.utils.entities.DefaultError

val PROJECT_NOT_FOUND = DefaultError("PROJECT_NOT_FOUND", "Project not found")
val PROJECT_TITLE_IS_EMPTY = DefaultError("PROJECT_TITLE_IS_EMPTY", "Project title is empty")
val PROJECT_COLOR_IS_EMPTY = DefaultError("PROJECT_COLOR_IS_EMPTY", "Project color is empty")