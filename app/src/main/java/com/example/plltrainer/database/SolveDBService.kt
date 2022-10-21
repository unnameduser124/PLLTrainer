package com.example.plltrainer.database

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.example.plltrainer.database.DatabaseConstants.SolveTable.DATE_TIME_OF_SOLVE
import com.example.plltrainer.database.DatabaseConstants.SolveTable.PLL_CASE
import com.example.plltrainer.database.DatabaseConstants.SolveTable.SOLVE_TABLE_NAME
import com.example.plltrainer.database.DatabaseConstants.SolveTable.SOLVE_TIME
import com.example.plltrainer.pllsolve.PLLCase
import com.example.plltrainer.pllsolve.Solve
import java.text.SimpleDateFormat
import java.util.*

class SolveDBService(context: Context) : SolvesDB(context) {

    fun saveSolveToDB(solve: Solve): Long {
        val db = this.writableDatabase
        val dtFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ROOT)
        val contentValues = ContentValues().apply {
            put(SOLVE_TIME, solve.time)
            put(PLL_CASE, solve.pllCase.toString())
            put(DATE_TIME_OF_SOLVE, dtFormat.format(solve.dateTime.time))
        }
        return db.insert(SOLVE_TABLE_NAME, null, contentValues)
    }

    fun loadAllSolvesFromDB(): MutableList<Solve> {
        val db = this.readableDatabase
        val projection = arrayOf(BaseColumns._ID, SOLVE_TIME, PLL_CASE, DATE_TIME_OF_SOLVE)

        val sortOrder = BaseColumns._ID
        val cursor = db.query(
            SOLVE_TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortOrder
        )
        val list = mutableListOf<Solve>()
        val dtFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ROOT)

        with(cursor) {
            while (cursor.moveToNext()) {
                val ID = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val solveTime = getFloat(getColumnIndexOrThrow(SOLVE_TIME))
                val PLLCase = getString(getColumnIndexOrThrow(PLL_CASE))
                val solveDateTime = getString(getColumnIndexOrThrow(DATE_TIME_OF_SOLVE))
                val parsedTime = dtFormat.parse(solveDateTime)
                val cal = Calendar.getInstance()
                cal.time = parsedTime
                list.add(Solve(solveTime, com.example.plltrainer.pllsolve.PLLCase.getPLLCaseFromString(PLLCase)!!, cal, ID))
            }
        }

        return list
    }
}