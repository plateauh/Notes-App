package com.najed.notesapp.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM Notes")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert
    fun addNote(note: Note)

    @Update
    fun updateNote(vararg notes: Note)

    @Delete
    fun deleteNote(note: Note)
}

