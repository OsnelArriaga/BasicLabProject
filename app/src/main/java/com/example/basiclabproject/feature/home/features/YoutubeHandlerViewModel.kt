package com.example.basiclabproject.feature.home.features

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basiclabproject.models.AspectosBasicosModel
import com.example.basiclabproject.models.YoutubeHandlerModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class YoutubeHandlerViewModel @Inject constructor() : ViewModel() {
    private val db = Firebase.firestore

    private val _videoUrl = mutableStateListOf<YoutubeHandlerModel>()
    val videoUrl: List<YoutubeHandlerModel> get() = _videoUrl

    private val _isLoading = mutableStateOf(true)
    val isLoading: Boolean get() = _isLoading.value

    init {
        readVideoUrls()
    }

    private fun readVideoUrls() {
        viewModelScope.launch {
            try {
                val result = db.collection("youtubeHandlerUrls")
                    .get()
                    .await()
                val userList = result.map { document ->
                    document.toObject(YoutubeHandlerModel::class.java).copy(id = document.id)
                }
                _videoUrl.addAll(userList)

                Log.w("Firebase", "URLS: $userList")
            } catch (e: Exception) {
                // Handle the error
            } finally {
                _isLoading.value = false
            }
        }
    }
}