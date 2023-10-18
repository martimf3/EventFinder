package com.example.eventfinder.data.firebase

import com.google.firebase.database.FirebaseDatabase

object DbProvider {
    private val database = FirebaseDatabase.getInstance()

    // Reference to your database root
    val rootReference = database.reference
}
