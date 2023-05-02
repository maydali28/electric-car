package com.example.electriccar.helpers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                TIMESTAMP_COl + " BIGINT," +
                KM_COL + " BIGINT" + ")")

        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addKm(timestamp : Long, km : String ){
        val values = ContentValues()

        values.put(TIMESTAMP_COl, timestamp)
        values.put(KM_COL, km)

        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getLastSavedRecord(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + "ORDER BY " + TIMESTAMP_COl + " DESC LIMIT 1", null)
    }

    companion object{
        private val DATABASE_NAME = "ELECTRIC_CAR"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "maintenance"
        val ID_COL = "id"
        val TIMESTAMP_COl = "timestamp"
        val KM_COL = "kilometer"
    }
}
