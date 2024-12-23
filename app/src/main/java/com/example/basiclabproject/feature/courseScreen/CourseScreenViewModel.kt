package com.example.basiclabproject.feature.courseScreen

import android.util.Log
import androidx.compose.material3.Card
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basiclabproject.models.CardInfo
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class CourseScreenViewModel @Inject constructor() : ViewModel() {

    private val db = Firebase.firestore

    var iCourseData = mutableStateOf(CardInfo())
        private set

    fun fetchItemById(cursoId: String) {
        viewModelScope.launch {
            try {
                val documentSnapshot = db.collection("aspectosBasicos")
                    .document(cursoId)
                    .get()
                    .await()
                val item = documentSnapshot.toObject(CardInfo::class.java)

                if (item != null) {
                    iCourseData.value = item
                }

                Log.d("iDescripcion:", iCourseData.value.toString())


            } catch (e: Exception) {
                // Manejar el error
            } finally {
            }
        }
    }
}

//    private val _cInfo = mutableStateListOf<CoursesInfo>()
//    val cInfo: List<CoursesInfo> get() = _cInfo
//
//    var xxx = mutableStateOf(CoursesInfo())
//
//    fun fetchCourses(documentId: String) {
//        viewModelScope.launch {
//            try {
//                val documentSnapshot = db.collection("aspectosBasicos")
//                    .document(documentId)
//                    .get()
//                    .await()
//                val item = documentSnapshot.toObject(CoursesInfo::class.java)
//                if (item != null) {
//                    xxx.value = item
//                }
//            } catch (e: Exception) {
//                // Manejar el error
//            } finally {
//
//            }
//
//        }
//    }
