package com.example.game_21.Model

import com.google.gson.annotations.SerializedName
import java.util.Objects

data class Cards(

    //acesando Sub Array
    @SerializedName("cards")
    var CardsDack: List<ItemCard>,
)

