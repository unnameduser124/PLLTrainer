package com.example.plltrainer.database

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.example.plltrainer.database.DatabaseConstants.SolveTable.DATE_TIME_OF_SOLVE
import com.example.plltrainer.database.DatabaseConstants.SolveTable.PLL_CASE
import com.example.plltrainer.database.DatabaseConstants.SolveTable.SOLVE_TABLE_NAME
import com.example.plltrainer.database.DatabaseConstants.SolveTable.SOLVE_TIME
import com.example.plltrainer.pllsolve.CaseAggregate
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
                if (parsedTime != null) {
                    cal.time = parsedTime
                }
                list.add(Solve(solveTime, com.example.plltrainer.pllsolve.PLLCase.getPLLCaseFromString(PLLCase)!!, cal, ID))
            }
        }

        list.sortByDescending { it.ID }
        return list
    }

    fun deleteSolve(solveID: Long): Int{
        val db = this.writableDatabase
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$solveID")

        return db.delete(SOLVE_TABLE_NAME, selection, selectionArgs)
    }

    fun caseAverageAndSolveNumber(): List<CaseAggregate>{
        val avgCalc = "sum(SolveTime)/count(_id) as MEAN"
        val numOfSolves = "count(_id) as SOLVES_NUM"

        val db = this.readableDatabase
        val projection = arrayOf(avgCalc, PLL_CASE, numOfSolves)

        val globalMeanCursor = db.query(
            SOLVE_TABLE_NAME,
            projection,
            null,
            null,
            PLL_CASE,
            null,
            null
        )
        val caseAggregateList = mutableListOf<CaseAggregate>()

        with(globalMeanCursor) {
            while (globalMeanCursor.moveToNext()) {
                val mean = getDouble(getColumnIndexOrThrow("MEAN"))
                val case = getString(getColumnIndexOrThrow(PLL_CASE))
                val number = getInt(getColumnIndexOrThrow("SOLVES_NUM"))
                caseAggregateList.add(CaseAggregate(PLLCase.getPLLCaseFromString(case)!!, mean, number))
            }
        }

        globalMeanCursor.close()
        PLLCase.values().toList().forEach {
            val mo50table = "(select * from Solves where PLLCase = '${it}' LIMIT 50)"

            val meanOf50Cursor = db.query(
                mo50table,
                projection,
                null,
                null,
                null,
                null,
                null
            )
            with(meanOf50Cursor){
                while(meanOf50Cursor.moveToNext()){
                    val mean = getDouble(getColumnIndexOrThrow("MEAN"))
                    val case = getString(getColumnIndexOrThrow(PLL_CASE))
                    if(case== null){
                        println(columnNames[1])
                        continue
                    }
                    val caseObject = PLLCase.getPLLCaseFromString(case)
                    val caseAggr = caseAggregateList.find { it.case == caseObject}
                    if(caseAggr!=null){
                        caseAggr.meanOf50 = mean
                    }
                }
            }
            meanOf50Cursor.close()
        }
        return caseAggregateList
    }

    fun getNumberOfSolves(): Int{
        val numOfSolves = "count(_id) as SOLVES_NUM"

        val db = this.readableDatabase
        val projection = arrayOf(numOfSolves)

        val cursor = db.query(
            SOLVE_TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )
        var number = 0

        with(cursor) {
            while (cursor.moveToNext()) {
                number = getInt(getColumnIndexOrThrow("SOLVES_NUM"))
            }
        }
        return number
    }

    fun getTotalTime(): Int{
        val totalTime = "sum($SOLVE_TIME)/60 as TOTAL_TIME"

        val db = this.readableDatabase
        val projection = arrayOf(totalTime)

        val cursor = db.query(
            SOLVE_TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )
        var number = 0

        with(cursor) {
            while (cursor.moveToNext()) {
                number = getInt(getColumnIndexOrThrow("TOTAL_TIME"))
            }
        }
        return number
    }

    fun getGlobalAverage(): Double{
        val avgTime = "avg($SOLVE_TIME) as AVG_TIME"

        val db = this.readableDatabase
        val projection = arrayOf(avgTime)

        val cursor = db.query(
            SOLVE_TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )
        var number = 0.0

        with(cursor) {
            while (cursor.moveToNext()) {
                number = getDouble(getColumnIndexOrThrow("AVG_TIME"))
            }
        }
        return number
    }

    fun clearSolveHistory(){
        val db = this.writableDatabase

        db.execSQL("DELETE FROM $SOLVE_TABLE_NAME")
    }
}