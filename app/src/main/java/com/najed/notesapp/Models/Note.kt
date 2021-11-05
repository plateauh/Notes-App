package com.najed.notesapp.Models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp


data class Note(val id: String,
                val content: String,
                @ServerTimestamp val timestamp: Timestamp? = Timestamp.now())