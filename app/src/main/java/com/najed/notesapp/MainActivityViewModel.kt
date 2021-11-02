package com.najed.notesapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.najed.notesapp.db.Note
import com.najed.notesapp.db.NoteDao
import com.najed.notesapp.db.NotesDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivityViewModel(app: Application): AndroidViewModel(app) {

    private val notesDao: NoteDao = NotesDatabase.getInstance(app).NoteDao()
    private val notes: LiveData<List<Note>>

    init {
        notes = notesDao.getAllNotes()
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return notes
    }

    fun addNote(note: Note) {
        CoroutineScope(IO).launch {
            notesDao.addNote(note)
        }
    }

    fun updateNote(oldNote: Note, newNote: Note) {
        CoroutineScope(IO).launch {
            notesDao.updateNote(oldNote, newNote)
        }
    }

    fun deleteNote(note: Note) {
        CoroutineScope(IO).launch {
            notesDao.deleteNote(note)
        }
    }
}