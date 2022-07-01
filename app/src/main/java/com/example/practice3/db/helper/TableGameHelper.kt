package com.example.practice3.db.helper

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.practice3.db.entities.GameContracts
import com.example.practice3.model.Game

class TableGameHelper {
    companion object{
        private val listGame = ArrayList<Game>()
        private var game: Game? = null
        fun insertGame(game: Game, db: SQLiteDatabase) {
            val data = ContentValues()
            data.put("name", game.name)
            data.put("company", game.company)
            data.put("category", game.category)
            data.put("image", game.image)
            data.put("description", game.description)
            db.insert(GameContracts.GameEntry.TABLE_NAME, null, data)
            db.close()
        }

        fun getAllGame(db: SQLiteDatabase): ArrayList<Game> {
            listGame.clear()
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

        fun getGame(id: Int?, db: SQLiteDatabase): Game? {
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

        fun deleteGame(id: Int?, db: SQLiteDatabase): Int {
            val args = arrayOf(id.toString())
            val deleteCount = db.delete(GameContracts.GameEntry.TABLE_NAME, "id = ?", args)
            db.close()
            return deleteCount
        }

        fun updateGame(game: Game?, id: Int?, db: SQLiteDatabase) {
            val data = ContentValues()
            data.put("name", game?.name)
            data.put("company", game?.company)
            data.put("category", game?.category)
            data.put("image", game?.image)
            data.put("description", game?.description)
            db.update(GameContracts.GameEntry.TABLE_NAME, data, "id = ?", arrayOf(id.toString()))
            db.close()
        }
    }
}