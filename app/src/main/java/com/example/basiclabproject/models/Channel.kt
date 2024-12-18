package com.example.basiclabproject.models

import com.google.firebase.database.DatabaseReference

data class Channel(
//    val id: DatabaseReference = "",
    val name: String = "",
    val createAt: Long = System.currentTimeMillis()
) {


}