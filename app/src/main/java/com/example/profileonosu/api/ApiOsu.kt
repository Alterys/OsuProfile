package com.example.profileonosu.api

import com.example.profileonosu.api.token.GetTokenRequest
import com.example.profileonosu.api.token.GetUserRequest
import com.example.profileonosu.api.token.Token
import com.example.profileonosu.api.token.UserInfo
import retrofit2.Call
import retrofit2.http.*

interface ApiOsu {
    @POST("oauth/token")
    suspend fun requestToken (
        @Body getTokenRequest: GetTokenRequest
    ): Call<Token>

    @GET("api/v2/users/{user}/osu")
    suspend fun requestUser (
        @Header("Authorization") token: String,
        @Body getUserRequest: GetUserRequest,
    ): UserInfo
}