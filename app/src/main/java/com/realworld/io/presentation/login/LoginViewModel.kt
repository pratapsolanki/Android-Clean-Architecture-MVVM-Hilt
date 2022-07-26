package com.realworld.io.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realworld.io.data.repo.Repositoryimpl
import com.realworld.io.domain.model.ArticleX
import com.realworld.io.domain.model.LoginInput
import com.realworld.io.domain.model.RegistrationFormState
import com.realworld.io.domain.model.UserLoginResponse
import com.realworld.io.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val articalRepository: Repositoryimpl) : ViewModel(){
    private val _loginState = MutableStateFlow<Resource<UserLoginResponse>>(Resource.Loading())
    val loginUIState: StateFlow<Resource<UserLoginResponse>> = _loginState

    fun loginWithState(loginInput: LoginInput) = viewModelScope.launch {
        _loginState.value = Resource.Loading()
        // fake network request time
        val response = articalRepository.login(loginInput)
        delay(2000L)
        if (response.isSuccessful) {
            _loginState.value =Resource.Success(response.body()!!)
        } else {
            _loginState.value = Resource.Error("Error Wrong Email or Password")
        }
    }



}

