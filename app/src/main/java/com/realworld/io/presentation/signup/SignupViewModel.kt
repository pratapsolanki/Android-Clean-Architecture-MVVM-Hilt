package com.realworld.io.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realworld.io.data.repo.Repositoryimpl
import com.realworld.io.domain.model.SignUpInput
import com.realworld.io.domain.model.UserLoginResponse
import com.realworld.io.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val articleRepository: Repositoryimpl) : ViewModel() {
    private val _signUpState = MutableStateFlow<Resource<UserLoginResponse>>(Resource.Loading())
    val signUpUIState: StateFlow<Resource<UserLoginResponse>> = _signUpState

    fun signup(signUpInput: SignUpInput) = viewModelScope.launch {
        _signUpState.value = Resource.Loading()
        // fake network request time
        val response = articleRepository.getSignup(signUpInput)
        delay(2000L)
        if (response.isSuccessful) {
            _signUpState.value =Resource.Success(response.body()!!)
        } else {
            _signUpState.value = Resource.Error("Error Wrong Email or Password")
        }
    }

}