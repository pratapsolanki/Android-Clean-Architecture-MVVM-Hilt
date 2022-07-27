package com.realworld.io.domain.model

data class RegistrationFormState(
    var email :String ="",
    val emailError : String ?= null,
    var password :String ="",
    val passwordError : String ?= null,
    var username :String ="",
    val usernameError : String ?= null,
)
