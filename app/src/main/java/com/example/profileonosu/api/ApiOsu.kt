package com.example.profileonosu.api

import androidx.constraintlayout.widget.Group
import com.example.profileonosu.api.token.GetTokenRequest
import com.example.profileonosu.api.token.Token
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiOsu {
    @POST("oauth/token")
    suspend fun requestToken (
        @Body getTokenRequest: GetTokenRequest
    ): Token






}