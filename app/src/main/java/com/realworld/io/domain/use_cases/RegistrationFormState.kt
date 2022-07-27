package com.realworld.io.domain.use_cases

data class RegistrationFormState(
    var email :String ="",
    val emailError : String ?= "",
    var password :String ="",
    val passwordError : String ?= "",
)
