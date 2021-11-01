package com.najed.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var notesRecyclerView: RecyclerView
    lateinit var messageEditText: EditText
    lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var notesList = DBHelper(applicationContext).getAllNotes()
        notesRecyclerView = findViewById(R.id.notes_rv)
        notesRecyclerView.adapter = Adapter(this, applicationContext, notesList)
        notesRecyclerView.layoutManager = LinearLayoutManager(this)

        messageEditText = findViewById(R.id.message_et)
        submitButton = findViewById(R.id.submit_btn)
        submitButton.setOnClickListener {
            if (messageEditText.text.toString().isNotEmpty()){
                val isSubmitted = DBHelper(applicationContext).storeNote(messageEditText.text.toString())
                if (isSubmitted) {
                    (notesRecyclerView.adapter as Adapter).update()
                    Toast.makeText(this, "Note added successfully!", Toast.LENGTH_SHORT).show()
                    messageEditText.setText("")
                }
                else
                    Toast.makeText(this, "Something went wrong :(", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show()
            }
        }
    }
}