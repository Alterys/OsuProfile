package com.example.profileonosu.api.userinfo

import com.google.gson.annotations.SerializedName

data class Score(
    @SerializedName("pp") val performancePoint: Float,
    @SerializedName("accuracy") val accuracy: Float,
    @SerializedName("rank") val rank: String,
    @SerializedName("max_combo") val maxCombo: Int,
    @SerializedName("beatmapset") val beatMapSet: Beatmapset,
    @SerializedName("beatmap") val beatMap: Beatmap,
    @SerializedName("mods") val mods: ArrayList<String>,
)
