package com.example.profileonosu.api.token

import com.google.gson.annotations.SerializedName

data class GetUserRequest (
    @SerializedName("user")  val user : String
        )