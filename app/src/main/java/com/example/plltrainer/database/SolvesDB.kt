package com.example.plltrainer.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.plltrainer.database.DatabaseConstants.SolveTableCreateQuery

open class SolvesDB(dbContext: Context): SQLiteOpenHelper(dbContext, "solves.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SolveTableCreateQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}