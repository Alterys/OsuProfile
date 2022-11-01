package com.example.profileonosu.api.userinfo

import com.google.gson.annotations.SerializedName

data class Score (
    @SerializedName("accuracy") val accuracy: Float,
)
