package com.realworld.io.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.realworld.io.data.repo.Repositoryimpl
import com.realworld.io.domain.model.LoginInput
import com.realworld.io.domain.model.UserLoginResponse
import com.realworld.io.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val articalRepository: Repositoryimpl) : ViewModel(){

    val loginResponseLiveData2 = MutableLiveData<Resource<UserLoginResponse>>()

    fun login(loginInput: LoginInput){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                loginResponseLiveData2.postValue(Resource.Loading())
                val response = articalRepository.login(loginInput)
                if (response.isSuccessful) {
                    loginResponseLiveData2.postValue(Resource.Success(response.body()!!))
                }else{
                    loginResponseLiveData2.postValue(Resource.Error("Error Wrong Email or Password"))
                }
            }
        }catch (e: Exception){
            loginResponseLiveData2.postValue(Resource.Error(e.message.toString()))
        }
    }

}