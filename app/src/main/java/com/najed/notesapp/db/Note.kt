package com.najed.notesapp.db

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import com.google.type.Date


data class Note(

        val id: String,

        val content: String,

        @ServerTimestamp
        val timestamp: Timestamp? = Timestamp.now())