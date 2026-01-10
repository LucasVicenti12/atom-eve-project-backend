package org.eve.domain.projects.exceptions

import org.eve.utils.entities.DefaultError

val PROJECT_NOT_FOUND = DefaultError("PROJECT_NOT_FOUND", "Project not found")
val PROJECT_TITLE_IS_EMPTY = DefaultError("PROJECT_TITLE_IS_EMPTY", "Project title is empty")
val PROJECT_COLOR_IS_EMPTY = DefaultError("PROJECT_COLOR_IS_EMPTY", "Project color is empty")
val PROJECT_YOU_ARENT_THE_OWNER = DefaultError("PROJECT_YOU_ARENT_THE_OWNER", "You aren't the owner of the project")
val PROJECT_YOU_CANNOT_ADD_YOURSELF = DefaultError("PROJECT_YOU_CANNOT_ADD_YOURSELF", "You cannot add yourself as a member")
val PROJECT_THIS_USER_ALREADY_ADDED = DefaultError("PROJECT_THIS_USER_ALREADY_ADDED", "This user is already added in the project")