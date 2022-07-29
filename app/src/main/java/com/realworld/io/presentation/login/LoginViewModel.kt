package com.realworld.io.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realworld.io.data.repo.RemoteRepositoryImpl
import com.realworld.io.domain.model.LoginInput
import com.realworld.io.domain.model.RegistrationFormState
import com.realworld.io.domain.model.UserLoginResponse
import com.realworld.io.domain.use_cases.ValidateEmail
import com.realworld.io.domain.use_cases.ValidatePassword
import com.realworld.io.util.Logger
import com.realworld.io.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Login ViewModel
 * Hilt ViewModel will create entry point for ViewModel
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val articleRepository: RemoteRepositoryImpl,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword)
    : ViewModel(){

    var state by mutableStateOf(RegistrationFormState())

    private val _loginState = MutableStateFlow<Resource<UserLoginResponse>>(Resource.Loading())
    val loginUIState: StateFlow<Resource<UserLoginResponse>> = _loginState

    /**
     * Login With State Flow
     */
    fun loginWithState(loginInput: LoginInput) {
        val emailResult = validateEmail.execute(loginInput.user.email)
        val passwordResult = validatePassword.execute(loginInput.user.password)

        Logger.d(emailResult.toString())
        Logger.d(passwordResult.toString())

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any{
            !it.successful
        }
        state = state.copy(
            emailError = emailResult.errorMessage,
            passwordError = passwordResult.errorMessage,
        )
        if(hasError) { return }

        viewModelScope.launch {
            _loginState.value = Resource.Loading()

            val response = articleRepository.login(loginInput)
            if (response.isSuccessful) {
                _loginState.value =Resource.Success(response.body()!!)
            } else {
                _loginState.value = Resource.Error("Error Wrong Email or Password")
            }
        }
    }

}

