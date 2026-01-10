package org.eve.domain.auth.entities

class Login(
    var username: String? = "",
    var password: String? = ""
)

class Token(
    var token: String? = "",
    var expiration: Long? = 0
)