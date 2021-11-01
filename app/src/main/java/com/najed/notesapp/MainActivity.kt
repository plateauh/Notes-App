package com.najed.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val messageEditText = findViewById<EditText>(R.id.message_et)
        val submitButton = findViewById<Button>(R.id.submit_btn)
        submitButton.setOnClickListener {
            val isSubmitted = DBHelper(applicationContext).storeNote("T1", messageEditText.text.toString())
            if (isSubmitted)
                Toast.makeText(this, "Note added successfully!", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Something went wrong :(", Toast.LENGTH_SHORT).show()
        }
    }
}