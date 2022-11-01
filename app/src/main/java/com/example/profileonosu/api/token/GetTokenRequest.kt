package com.example.profileonosu.api.token

import com.google.gson.annotations.SerializedName

data class GetTokenRequest(
    @SerializedName("client_id")  val clientId : String,
    @SerializedName("client_secret") val clientSecret : String,
    @SerializedName("grant_type") val grantType : String,
    @SerializedName("scope") val scope : String,
)