package com.realworld.io.data.repo


import com.realworld.io.domain.remote.ApiService
import com.realworld.io.model.*
import retrofit2.Response
import javax.inject.Inject

class Repositoryimpl @Inject constructor(private val apiService: ApiService)  {
     suspend fun getArticle(): Response<Article> = apiService.article()

     suspend fun getSignup(signUpInput: SignUpInput) : Response<UserLoginResponse> = apiService.signup(signUpInput)

     suspend fun login(loginInput: LoginInput) : Response<UserLoginResponse> = apiService.login(loginInput)
}