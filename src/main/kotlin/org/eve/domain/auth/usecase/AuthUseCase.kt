package org.eve.domain.auth.usecase

import org.eve.domain.auth.entities.Login
import org.eve.domain.auth.entities.Token
import org.eve.domain.users.entities.User
import org.eve.utils.entities.DefaultResponse

interface AuthUseCase {
    fun login(login: Login): DefaultResponse<Token>
    fun me(): DefaultResponse<User>
}