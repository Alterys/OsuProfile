package com.example.profileonosu.api.userinfo

import com.google.gson.annotations.SerializedName

data class Beatmap(
    @SerializedName("version") val difficulty: String,
    @SerializedName("url") val urlMap: String,
)