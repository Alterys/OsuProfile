package com.example.profileonosu.api

import com.example.profileonosu.api.token.GetTokenRequest
import com.example.profileonosu.api.token.Token
import com.example.profileonosu.api.token.UserInfo
import retrofit2.Call
import retrofit2.http.*

interface ApiOsu {
    @POST("oauth/token")
    fun requestToken (
        @Body getTokenRequest: GetTokenRequest
    ): Call<Token>

    @GET("api/v2/users/{user}/osu")
    fun requestUser (
        @Header("Authorization") token: String,
        @Path("user") user: String
    ): Call<UserInfo>

    @GET("/api/v2/users/{id}/scores/best")
    fun requestScores (
        @Header("Authorization") token: String,

    ): UserInfo
}