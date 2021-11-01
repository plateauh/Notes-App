package com.najed.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var notesRecyclerView: RecyclerView
    lateinit var messageEditText: EditText
    lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var notesList = listOf<Note>()
        notesRecyclerView = findViewById(R.id.notes_rv)
        notesRecyclerView.layoutManager = LinearLayoutManager(this)
        CoroutineScope(IO).launch {
            notesList = NotesDatabase.getInstance(applicationContext).NoteDao().getAllNotes()
            withContext(Main){
                notesRecyclerView.adapter = Adapter(this@MainActivity, notesList)
            }
        }

        messageEditText = findViewById(R.id.message_et)
        submitButton = findViewById(R.id.submit_btn)
        submitButton.setOnClickListener {
            if (messageEditText.text.toString().isNotEmpty()){
                CoroutineScope(IO).launch {
                    NotesDatabase.getInstance(applicationContext).NoteDao().addNote(Note(0, messageEditText.text.toString()))
                    notesList = NotesDatabase.getInstance(this@MainActivity).NoteDao().getAllNotes()
                    withContext(Main){
                        notesRecyclerView.adapter = Adapter(this@MainActivity, notesList)
                    }
                }
                messageEditText.setText("")
            }
            else {
                Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show()
            }
        }
    }
}