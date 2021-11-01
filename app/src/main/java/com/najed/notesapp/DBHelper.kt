package com.najed.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, "notes.db", null, 1) {

    private var SQLiteDatabase: SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table Notes (Title text, Content text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun storeNote (title: String, content: String): Boolean {
        val contentValues = ContentValues()
        contentValues.put("Title", title)
        contentValues.put("Content", content)
        var status = SQLiteDatabase.insert("Notes", null, contentValues)
        return status != -1L
    }

    fun getNote (title: String): String {
        val cursor: Cursor = SQLiteDatabase.query("Notes", null, "Title=?", arrayOf(title), null, null, null)
        cursor.moveToFirst()
        return cursor.getString(cursor.getColumnIndex("Content"))
    }
}