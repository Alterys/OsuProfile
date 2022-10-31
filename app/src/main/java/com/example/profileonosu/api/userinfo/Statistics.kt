package com.example.profileonosu.api.userinfo

import com.google.gson.annotations.SerializedName

data class Statistics(
    @SerializedName("pp") val pp: String,
    @SerializedName("global_rank") val globalRank: String,
    )
