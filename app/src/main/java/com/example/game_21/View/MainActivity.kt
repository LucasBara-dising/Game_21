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
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    var deckId:String=""
    var player=""
    var turn:Int = 0

    //Sqlite
    private var cardList = ArrayList<ItemCard>()
    var databaseHelper = DBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnMoreCard.setOnClickListener{
            moreOneCard()
        }
        creteDeck()
    }

    //cria e recebe o link do deck
    private fun creteDeck(){
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
                turnPlayer(0)
            }
        })
    }

    fun turnPlayer(turn:Int):String{
        Log.d("turn: ", turn.toString())
            if(turn==0 ||turn==3){
                player="p1"
                getCards()
            }

            else if(turn==1 ||turn==4){
                player="p2"
                getCards()
            }

            else if(turn==2 ||turn==5){
                player="p3"
                getCards()

            }
        else{
                Log.d("turn: ", "stop")
                listCards("p2")
            }

        return  player
    }
    private fun getCards(){
        val url= "https://deckofcardsapi.com/api/deck/$deckId/draw/"

        val retrofitClient= NetworkUtils.getRetrofitInstace(url)

        val endPoint= retrofitClient.create(EndPoint::class.java)
        //To do receber mais de uma carta
        val callback= endPoint.GetCards(1)

        callback.enqueue(object : retrofit2.Callback<Cards> {
            override fun onFailure(call: Call<Cards>, t: Throwable) {
                Log.d("Erro",t.message.toString())
            }

            override fun onResponse(call: Call<Cards>, response: Response<Cards>) {
                val cards: Cards? =response.body()
                cards?.CardsDack?.forEach {

                    //add no sqlite
                    databaseHelper.addCard(player, it.CardsValue, it.CardsImg)

                    Log.d("Cards Value",it.CardsValue)
                    Log.d("Cards Img",it.CardsImg)
                    Log.d("Player Jogada:",player)

                    turn++
                    turnPlayer(turn)
                }
            }
        })
    }

    private fun moreOneCard(){
        val url= "https://deckofcardsapi.com/api/deck/$deckId/draw/"

        val retrofitClient= NetworkUtils.getRetrofitInstace(url)

        val endPoint= retrofitClient.create(EndPoint::class.java)
        //To do receber mais de uma carta
        val callback= endPoint.GetCards(1)

        callback.enqueue(object : retrofit2.Callback<Cards> {
            override fun onFailure(call: Call<Cards>, t: Throwable) {
                Log.d("Erro",t.message.toString())
            }

            override fun onResponse(call: Call<Cards>, response: Response<Cards>) {
                val cards: Cards? =response.body()
                cards?.CardsDack?.forEach {

                    //add no sqlite
                    databaseHelper.addCard("p2", it.CardsValue, it.CardsImg)

                    Log.d("Cards Value",it.CardsValue)
                    Log.d("Cards Img",it.CardsImg)
                }
            }
        })
    }

    private fun listCards(player2:String){
        cardList=databaseHelper.getCardPlayer(player2)
        Log.d("Cards Player", cardList.toString())
    }



}

