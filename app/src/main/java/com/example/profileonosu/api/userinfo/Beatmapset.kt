package com.example.profileonosu.api.userinfo

import com.google.gson.annotations.SerializedName

data class Beatmapset(
    @SerializedName("title") val title: String,
    @SerializedName("artist") val artist: String,
)