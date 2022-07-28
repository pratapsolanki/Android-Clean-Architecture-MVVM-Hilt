package com.realworld.io.domain.model

data class RegistrationFormState(
    var email :String ="",
    val emailError : String ?= null,
    var password :String ="",
    val passwordError : String ?= null,
    var username :String ="",
    val usernameError : String ?= null,
    var title :String ="",
    val titleError : String ?= null,
    var shortDesc :String ="",
    val shortDescError : String ?= null,
    var description :String ="",
    val descriptionError : String ?= null,
)
