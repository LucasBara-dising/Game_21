package com.example.game_21.Controller

import com.example.game_21.Model.Cards
import com.example.game_21.Model.Deck
import com.example.game_21.Model.ItemCard
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EndPoint {

    //cria deck
    @GET("?")
    fun CreteDeck(@Query("?deck_count") numDeck:Int): Call<Deck>

    //Recebe cartas
    @GET("?")
    fun GetCards(@Query("?count") numOfCards:Int): Call<Cards>
}