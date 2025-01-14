package com.example.basiclabproject.feature.courseScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basiclabproject.models.AspectosBasicosModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CourseScreenViewModel @Inject constructor() : ViewModel() {

    private val db = Firebase.firestore

    private val _courseContent = MutableLiveData<AspectosBasicosModel?>()
    val cContent: LiveData<AspectosBasicosModel?> get() = _courseContent

    fun fetchData(documentId: String) {
        try {
            db.collection("aspectosBasicos")
                .document(documentId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        // Mapeo del documento de acuerdo al modelo
                        val usuarioData = document.toObject<AspectosBasicosModel>()
                        _courseContent.value = usuarioData
                    } else {
                        // Manejar el caso en que el documento no existe
                        TODO("Error con el documento")
                    }
                    Log.d("lLiveResponse:", _courseContent.value.toString())
                }
                .addOnFailureListener { exception ->
                    // Manejar errores
                    _courseContent.value = null
                }
        } catch (e: Exception) {
            TODO("Error al momento de consultar la coleccion")
        }
    }

}
