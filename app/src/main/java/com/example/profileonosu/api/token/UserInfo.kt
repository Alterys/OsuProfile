package com.example.profileonosu.api.token

import com.google.gson.annotations.SerializedName

data class UserInfo (
    @SerializedName("username") val username : String,
    @SerializedName("pp") val pp: String,
    @SerializedName("global_rank") val globalRank: String,
    @SerializedName("country_code") val countryCode: String,
)