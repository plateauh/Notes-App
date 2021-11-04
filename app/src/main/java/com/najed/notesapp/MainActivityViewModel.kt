package com.najed.notesapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.najed.notesapp.db.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivityViewModel(app: Application): AndroidViewModel(app) {

    private val notesCollection = FirebaseFirestore.getInstance().collection("Notes")
    private val notes: MutableLiveData<List<Note>> = MutableLiveData()

    fun getNotes(): LiveData<List<Note>>{
        setNotes()
        return notes
    }

    private fun setNotes() {
        val temp = arrayListOf<Note>()
        notesCollection.get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        temp.add(Note(document.id, document["content"].toString()))
                    }
                    temp.sortBy { it.content }
                    notes.postValue(temp)
                }
                .addOnFailureListener { e ->
                    Log.d("Note", "Exception setting notes ${e.message}")
                }
    }

    fun addNote(note: Note) {
        CoroutineScope(IO).launch {
            val noteHashMap = hashMapOf("content" to note.content)
            notesCollection.add(noteHashMap)
                    .addOnSuccessListener {
                        Log.d("Note", "Note added")
                    }
                    .addOnFailureListener {
                        Log.d("Note", "Note not added: ${it.message}")
                    }
            setNotes()
        }
    }

    fun updateNote(oldNote: Note, newNote: Note) {
        val document = notesCollection.document(oldNote.id)
        document.update("content", newNote.content)
            .addOnSuccessListener {
                Log.d("Note", "Note ${oldNote.id} updated")
            }
            .addOnFailureListener {
                Log.d("Note", "Note ${oldNote.id} not updated \n${it.message}")
            }
        setNotes()
    }

    fun deleteNote(note: Note) {
        val document = notesCollection.document(note.id)
        document.delete()
            .addOnSuccessListener {
                Log.d("Note", "Note ${note.id} deleted")
            }
            .addOnFailureListener {
                Log.d("Note", "Note ${note.id} not deleted")
            }
        setNotes()
    }
}