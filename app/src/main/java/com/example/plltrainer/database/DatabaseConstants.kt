package com.example.plltrainer.database

import android.provider.BaseColumns
import com.example.plltrainer.database.DatabaseConstants.SolveTable.DATE_TIME_OF_SOLVE
import com.example.plltrainer.database.DatabaseConstants.SolveTable.PLL_CASE
import com.example.plltrainer.database.DatabaseConstants.SolveTable.SOLVE_TABLE_NAME
import com.example.plltrainer.database.DatabaseConstants.SolveTable.SOLVE_TIME

object DatabaseConstants {

    object SolveTable : BaseColumns {
        const val SOLVE_TABLE_NAME = "Solves"
        const val SOLVE_TIME = "SolveTime"
        const val PLL_CASE = "PLLCase"
        const val DATE_TIME_OF_SOLVE = "DateOfSolve"
    }

    const val SolveTableCreateQuery = "CREATE TABLE $SOLVE_TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$SOLVE_TIME REAL NOT NULL," +
            "$PLL_CASE TEXT NOT NULL," +
            "$DATE_TIME_OF_SOLVE TEXT NOT NULL)"

}