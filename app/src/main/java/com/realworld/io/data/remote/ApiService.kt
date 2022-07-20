package com.realworld.io.data.remote


import com.realworld.io.model.*
import com.realworld.io.util.ApiConstants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {

    @POST(ApiConstants.SIGNUP)
    suspend fun signup(@Body signUpInput: SignUpInput) : Response<UserLoginResponse>

    @GET(ApiConstants.ARTICLES)
    suspend fun article() : Response<Article>

    @POST(ApiConstants.SIGNIN)
    suspend fun login(@Body loginInput: LoginInput) : Response<UserLoginResponse>

}