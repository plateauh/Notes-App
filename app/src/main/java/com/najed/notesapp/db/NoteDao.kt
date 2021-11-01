package com.najed.notesapp.db

import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM Notes")
    fun getAllNotes(): List<Note>

    @Insert
    fun addNote(note: Note)

    @Update
    fun updateNote(vararg notes: Note)

    @Delete
    fun deleteNote(note: Note)
}

