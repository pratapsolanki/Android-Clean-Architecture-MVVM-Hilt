package com.realworld.io.presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realworld.io.data.repo.RemoteRepositoryImpl
import com.realworld.io.domain.model.SignUpInput
import com.realworld.io.domain.model.UserLoginResponse
import com.realworld.io.domain.model.RegistrationFormState
import com.realworld.io.domain.use_cases.ValidateEmail
import com.realworld.io.domain.use_cases.ValidatePassword
import com.realworld.io.domain.use_cases.ValidateUsername
import com.realworld.io.util.Logger
import com.realworld.io.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val articleRepository: RemoteRepositoryImpl,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validateUsername: ValidateUsername
) : ViewModel() {

    //use case validation clean
    var state by mutableStateOf(RegistrationFormState())

    private val _signUpState = MutableStateFlow<Resource<UserLoginResponse>>(Resource.Loading())
    val signUpUIState: StateFlow<Resource<UserLoginResponse>> = _signUpState

    fun signup(signUpInput: SignUpInput){
        val emailResult = validateEmail.execute(signUpInput.user.email)
        val passwordResult = validatePassword.execute(signUpInput.user.password)
        val usernameResult = validateUsername.execute(signUpInput.user.password)

        Logger.d(emailResult.toString())
        Logger.d(passwordResult.toString())

        val hasError = listOf(
            emailResult,
            passwordResult,
            usernameResult
        ).any{
            !it.successful
        }
        state = state.copy(
            emailError = emailResult.errorMessage,
            passwordError = passwordResult.errorMessage,
            usernameError = usernameResult.errorMessage,
        )
        if(hasError) { return }
        viewModelScope.launch {
            _signUpState.value = Resource.Loading()
            val response = articleRepository.getSignup(signUpInput)
            if (response.isSuccessful) {
                _signUpState.value =Resource.Success(response.body()!!)
            } else {
                _signUpState.value = Resource.Error("Error Wrong Email or Password")
            }
        }
    }

}