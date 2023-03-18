package com.example.game_21.Model

import com.google.gson.annotations.SerializedName

data class ItemCard(
    @SerializedName("value")
    var CardsValue: Int,

    @SerializedName("image")
    var CardsImg: String,

    var Player: String
)
