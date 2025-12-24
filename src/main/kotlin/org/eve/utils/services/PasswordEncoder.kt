package org.eve.utils.services

import jakarta.enterprise.context.ApplicationScoped
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@ApplicationScoped
class PasswordEncoder: BCryptPasswordEncoder()