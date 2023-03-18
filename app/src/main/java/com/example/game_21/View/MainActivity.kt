package com.example.game_21.View

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.game_21.Controller.EndPoint
import com.example.game_21.Controller.NetworkUtils
import com.example.game_21.Model.Cards
import com.example.game_21.Model.Deck
import com.example.game_21.Model.ItemCard
import com.example.game_21.R
import com.example.game_21.Service.DBHelper
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    var deckId:String=""

    //Sqlite
    var cardList = ArrayList<ItemCard>()
    var databaseHelper = DBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        creteDeck()
    }

    //cria e recebe o link do deck
    fun creteDeck(){
        val retrofitClient= NetworkUtils.getRetrofitInstace("https://deckofcardsapi.com/api/deck/new/shuffle/")

        val endPoint= retrofitClient.create(EndPoint::class.java)
        val callback= endPoint.CreteDeck(6)

        callback.enqueue(object : retrofit2.Callback<Deck> {
            override fun onFailure(call: Call<Deck>, t: Throwable) {
                Log.d("Id",t.message.toString())
            }

            override fun onResponse(call: Call<Deck>, response: Response<Deck>) {
                val deck: Deck? =response.body()
                deckId=deck?.Id_Deck.toString()

                getCards()
            }
        })
    }

    fun getCards(){
        val url= "https://deckofcardsapi.com/api/deck/$deckId/draw/"

        val retrofitClient= NetworkUtils.getRetrofitInstace(url)

        val endPoint= retrofitClient.create(EndPoint::class.java)
        //To do receber mais de uma carta
        val callback= endPoint.GetCards(1)

        callback.enqueue(object : retrofit2.Callback<Cards> {
            override fun onFailure(call: Call<Cards>, t: Throwable) {
                Log.d("Cards",t.message.toString())
            }

            override fun onResponse(call: Call<Cards>, response: Response<Cards>) {
                val cards: Cards? =response.body()
                cards?.CardsDack?.forEach {
                    Log.d("Cards Value",it.CardsValue.toString())
                    Log.d("Cards Img",it.CardsImg)
                }
            }
        })
    }

}

