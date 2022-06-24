package com.example.practice3.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.practice3.db.entities.GameContracts
import com.example.practice3.model.Game

class SQLiteHelperGame(private val context: Context) :
    SQLiteOpenHelper(context, "games.db", null, 1) {
    private val listGame = ArrayList<Game>()
    private var game: Game? = null

    companion object {
        const val QUERY_INSERT = "CREATE TABLE ${GameContracts.GameEntry.TABLE_NAME} " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${GameContracts.GameEntry.COLUMN_NAME} TEXT," +
                "${GameContracts.GameEntry.COLUMN_COMPANY} TEXT," +
                "${GameContracts.GameEntry.COLUMN_CATEGORY} TEXT," +
                "${GameContracts.GameEntry.COLUMN_IMAGE} TEXT," +
                "${GameContracts.GameEntry.COLUMN_DESCRIPTION} TEXT)"
        const val QUERY_DROP = "DROP TABLE IF EXISTS ${GameContracts.GameEntry.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(QUERY_INSERT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(QUERY_DROP)
        onCreate(db)
    }

    fun insertGame(game: Game) {
        val data = ContentValues()
        data.put("name", game.name)
        data.put("company", game.company)
        data.put("category", game.category)
        data.put("image", game.image)
        data.put("description", game.description)
        val db = this.writableDatabase
        db.insert(GameContracts.GameEntry.TABLE_NAME, null, data)
        db.close()
    }

    fun getAllGame(): ArrayList<Game> {
        listGame.clear()
        val db: SQLiteDatabase = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM ${GameContracts.GameEntry.TABLE_NAME}",
            null
        )
        if (cursor.moveToFirst()) {
            do {
                game = Game(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
                )
                listGame.add(game!!)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return listGame
    }

    fun getGame(id: Int?): Game? {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT id," +
                    "${GameContracts.GameEntry.COLUMN_NAME}," +
                    "${GameContracts.GameEntry.COLUMN_COMPANY}," +
                    "${GameContracts.GameEntry.COLUMN_CATEGORY}," +
                    "${GameContracts.GameEntry.COLUMN_IMAGE}," +
                    "${GameContracts.GameEntry.COLUMN_DESCRIPTION} " +
                    "FROM ${GameContracts.GameEntry.TABLE_NAME} " +
                    " WHERE id = $id",
            null
        )
        if (cursor.moveToFirst()) {
            do {
                game = Game(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return game
    }

    fun deleteGame(id: Int?): Int {
        val args = arrayOf(id.toString())
        val db = this.writableDatabase
        val deleteCount = db.delete(GameContracts.GameEntry.TABLE_NAME, "id = ?", args)
        db.close()
        return deleteCount
    }
}