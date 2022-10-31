package com.example.profileonosu.api.userinfo

import com.google.gson.annotations.SerializedName

data class UserInfo (
    @SerializedName("username") val username: String,
    @SerializedName("country_code") val countryCode: String,
    @SerializedName("id") val id: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("statistics") val statistics: Statistics,

)