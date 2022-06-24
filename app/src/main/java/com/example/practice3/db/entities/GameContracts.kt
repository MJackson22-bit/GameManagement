package com.example.practice3.db.entities

import android.provider.BaseColumns

class GameContracts {
    class GameEntry : BaseColumns{
        companion object{
            const val TABLE_NAME = "game"
            const val COLUMN_NAME = "name"
            const val COLUMN_COMPANY = "company"
            const val COLUMN_CATEGORY = "category"
            const val COLUMN_IMAGE = "image"
            const val COLUMN_DESCRIPTION = "description"
        }
    }
}