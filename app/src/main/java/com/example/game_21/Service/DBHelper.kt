package com.example.game_21.Service

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.game_21.Model.ItemCard

class DBHelper (context: Context):SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_VERSION = 1
        private const val DB_NAME = "StoreCards"
        private const val TABLE_NAME = "TB_Cards"
        private const val ID = "Id"
        private const val PLAYER = "Player"
        private const val VALUE_CARD = "Value_Card"
        private const val IMG_CARD = "Img_Card"
    }

    override fun onCreate(db: SQLiteDatabase?) {
       val CREATE_TABLE=
           "CREATE TABLE $TABLE_NAME ($ID INTEGER  PRIMARY  KEY AUTOINCREMENT, $PLAYER TEXT, $VALUE_CARD TEXT , $IMG_CARD TEXT);"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
       val DROP_TABLE= "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun addCard(Player:String, valueCard:String, ImgCard:String ){
        val db=this.writableDatabase
        val values= ContentValues()

        values.put(PLAYER,Player)
        values.put(VALUE_CARD,valueCard)
        values.put(IMG_CARD,ImgCard)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }


    @SuppressLint("Range")
    //TO DO: Resolver SuppressLint
    fun getCardPlayer(_player:String): ArrayList<ItemCard>{
        val itemCardList= ArrayList<ItemCard>()
        val db= this.readableDatabase
        val selectQuery= "SELECT * FROM $TABLE_NAME WHERE $PLAYER =? "
        val cursor=db.rawQuery(selectQuery, arrayOf(_player))
        if (cursor !=null){
            if(cursor.moveToFirst()){
                do{
                    val itemCard= ItemCard()
                    itemCard.IdCard= cursor.getInt(cursor.getColumnIndex((ID)))
                    itemCard.Player= cursor.getString(cursor.getColumnIndex((PLAYER)))
                    itemCard.CardsValue= cursor.getString(cursor.getColumnIndex((VALUE_CARD)))
                    itemCard.CardsImg= cursor.getString(cursor.getColumnIndex((IMG_CARD)))
                    itemCardList.add(itemCard)
                }while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return itemCardList
    }

    fun deleteAllCards(): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, null,null).toLong()
        db.close()
        return ("$_success").toInt() != -1
    }

}

