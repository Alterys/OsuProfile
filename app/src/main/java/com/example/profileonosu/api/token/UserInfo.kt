package com.example.profileonosu.api.token

import com.google.gson.annotations.SerializedName

data class UserInfo (
    @SerializedName("username") val username : String,
        )