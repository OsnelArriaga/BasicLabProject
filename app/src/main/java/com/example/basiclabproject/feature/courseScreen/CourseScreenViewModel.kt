package com.example.basiclabproject.feature.courseScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basiclabproject.models.CardInfo
import com.example.basiclabproject.models.CoursesInfo
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

    private val _coursesInfo = mutableStateListOf<CoursesInfo>()
    val coursesInfo: List<CoursesInfo> get() = _coursesInfo

    private val _isLoading = mutableStateOf(true)
    val isLoading: Boolean get() = _isLoading.value

    init {
        getCoursesInfo()
    }

    private fun getCoursesInfo() {
        viewModelScope.launch {
            try {
                val result = db.collection("aspectosBasicos")
                    .get()
                    .await()
                val coursesInfoList = result.map { document ->

                    document.toObject(CoursesInfo::class.java).copy(id = document.id)

                }
                _coursesInfo.addAll(coursesInfoList)
            } catch (e: Exception) {
                // Handle the error
            } finally {
                _isLoading.value = false
            }
        }
    }

    private var _iDificultad = mutableStateListOf<CoursesInfo>()

    var iDificultad: List<CoursesInfo> = _iDificultad

    var iTopicos = mutableStateOf("")
        private set
    var iDescripcion = mutableStateOf("")
        private set
    var iLecciones = mutableStateOf("")
        private set

//    fun fetchItemById(documentId: String) {
//
//        viewModelScope.launch {
//            try {
//                val documentSnapshot = db.collection("aspectosBasicos")
//                    .document(documentId)
//                    .get()
//                    .await()
//                val item = documentSnapshot.toObject(CoursesInfo::class.java)
//                if (item != null) {
//                    _iDificultad.add(
//                        CoursesInfo(
//                            item.dificultad,
//                            item.descripcion,
//                            item.lecciones)
//                    )
//                }
//
//            } catch (e: Exception) {
//                // Manejar el error
//            } finally {
//            }
//        }
//    }

    private val _cInfo = mutableStateListOf<CoursesInfo>()
    val cInfo: List<CoursesInfo> get() = _cInfo

    var xxx = mutableStateOf(CoursesInfo())

    fun fetchCourses(documentId: String) {
        viewModelScope.launch {
            try {
                val documentSnapshot = db.collection("aspectosBasicos")
                    .document(documentId)
                    .get()
                    .await()
                val item = documentSnapshot.toObject(CoursesInfo::class.java)
                if (item != null) {
                    xxx.value = item
                }
            } catch (e: Exception) {
                // Manejar el error
            } finally {

            }

        }
    }
}