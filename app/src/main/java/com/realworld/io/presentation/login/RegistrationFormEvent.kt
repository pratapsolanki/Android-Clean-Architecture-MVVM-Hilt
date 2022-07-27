package com.realworld.io.presentation.login

sealed class RegistrationFormEvent{
    data class EmailChanged(val email :String) :RegistrationFormEvent()
    data class PasswordChanged(val password : String ) : RegistrationFormEvent()
    object Submit : RegistrationFormEvent()
}
