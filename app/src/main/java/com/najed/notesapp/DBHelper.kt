package com.najed.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, "notes.db", null, 1) {

    private var SQLiteDatabase: SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table Notes (Content text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun storeNote(content: String): Boolean {
        val contentValues = ContentValues()
        contentValues.put("Content", content)
        var status = SQLiteDatabase.insert("Notes", null, contentValues)
        return status != -1L
    }

    fun getAllNotes(): ArrayList<String> {
        val notes = arrayListOf<String>()
        val cursor: Cursor = SQLiteDatabase.query("Notes", null, null, null, null, null, null)
        if (cursor.count != 0){
            cursor.moveToFirst()
            do {
                notes.add(cursor.getString(cursor.getColumnIndex("Content")))
            } while (cursor.moveToNext())
        }
        return notes
    }

    fun updateNote(oldContent: String, newContent: String): Boolean {
        val contentValues = ContentValues()
        contentValues.put("Content", newContent)
        val status = SQLiteDatabase.update("Notes", contentValues, "Content=?", arrayOf(oldContent))
        return status > 0
    }

    fun deleteNote(content: String): Boolean {
        val status = SQLiteDatabase.delete("Notes", "Content=?", arrayOf(content))
        return status > 0
    }
}