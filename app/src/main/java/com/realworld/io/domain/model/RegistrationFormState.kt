package com.realworld.io.domain.model

data class RegistrationFormState(
    val email :String ="",
    val emailError : String ?= null,
    val password :String ="",
    val passwordError : String ?= null,
)
