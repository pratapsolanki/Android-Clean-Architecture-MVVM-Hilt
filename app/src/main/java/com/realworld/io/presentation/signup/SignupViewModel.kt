package com.realworld.io.presentation.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.realworld.io.data.repo.Repositoryimpl
import com.realworld.io.domain.model.SignUpInput
import com.realworld.io.domain.model.UserLoginResponse
import com.realworld.io.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(val articalRepository: Repositoryimpl) : ViewModel() {
    val loginResponseLiveData = MutableLiveData<Resource<UserLoginResponse>>()

    fun signup(signUpInput: SignUpInput){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = articalRepository.getSignup(signUpInput)
                if (response.isSuccessful) {
                    loginResponseLiveData.postValue(Resource.Success(response.body()!!))
                }else{
                    loginResponseLiveData.postValue(Resource.Error("Something Went wrong"))
                }
            }
        }catch (e: Exception){
            loginResponseLiveData.postValue(Resource.Error(e.message.toString()))
        }

    }
}