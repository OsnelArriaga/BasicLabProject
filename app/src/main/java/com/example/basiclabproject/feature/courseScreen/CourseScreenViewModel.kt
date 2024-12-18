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
        loadDocuments()
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

//    Quiero que al darle click a un elemento de una lista de documentos de mi coleccion en firebase, me despliegue una vista con el contenido en especifico del documento seleccionado y si retrocedo y selecciono otro documento, el contenido de esa vista cambiara al contenido del otro documento seleciconado usando Kotlin y jetpackcompose con viewmodels

    private val documentsCollection = db.collection("your_collection_name") // Reemplaza con el nombre de tu colección

    val documents = mutableStateListOf<CoursesInfo>()
    var selectedDocument by mutableStateOf<CoursesInfo?>(null)

    private fun loadDocuments() {
        viewModelScope.launch {
            val querySnapshot = documentsCollection.get().await()
            querySnapshot.documents.forEach { document ->
                val documentData = document.toObject(CoursesInfo::class.java)
                documentData?.let {
                    documents.add(it)
                }
            }
        }
    }

    fun selectDocument(document: CoursesInfo) {
        selectedDocument = document
    }



}