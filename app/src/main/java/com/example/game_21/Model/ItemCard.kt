package com.example.game_21.Model

import com.google.gson.annotations.SerializedName

data class ItemCard(
    @SerializedName("image")
    var CardsImg: String=" ",

    @SerializedName("value")
    var CardsValue: String="",

    var Player: String=" "
)
