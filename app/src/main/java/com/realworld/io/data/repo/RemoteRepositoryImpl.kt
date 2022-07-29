package com.realworld.io.data.repo


import com.realworld.io.data.remote.ApiService
import com.realworld.io.domain.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject


/**
 * Remote Repository class implementation
 * This class will communication with view Model , Activity not ApiService
 */
class RemoteRepositoryImpl @Inject constructor(private val apiService: ApiService)  {

     /**
      * Get record from Remote
      */
     suspend fun getArticle(): Response<Article> = apiService.article()


     /**
      * Signup
      */
     suspend fun getSignup(signUpInput: SignUpInput) : Response<UserLoginResponse> = apiService.signup(signUpInput)


     /**
      * Login
      */
     suspend fun login(loginInput: LoginInput) : Response<UserLoginResponse> = apiService.login(loginInput)
}