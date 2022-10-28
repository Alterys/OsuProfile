package com.example.profileonosu.api.token

import com.google.gson.annotations.SerializedName

data class Token (
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
)


