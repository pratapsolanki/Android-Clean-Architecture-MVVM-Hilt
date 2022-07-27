package com.realworld.io.data.repo


import com.realworld.io.data.remote.ApiService
import com.realworld.io.domain.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val apiService: ApiService)  {
     suspend fun getArticle(): Response<Article> = apiService.article()

     suspend fun getSignup(signUpInput: SignUpInput) : Response<UserLoginResponse> = apiService.signup(signUpInput)

     suspend fun login(loginInput: LoginInput) : Response<UserLoginResponse> = apiService.login(loginInput)
}