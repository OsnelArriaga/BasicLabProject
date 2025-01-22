package com.example.basiclabproject.feature.courseScreen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basiclabproject.models.AspectosBasicosModel
import com.example.basiclabproject.models.YoutubeHandlerModel
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class CourseScreenViewModel @Inject constructor() : ViewModel() {

    private val db = Firebase.firestore

    private val _courseContent = MutableLiveData<AspectosBasicosModel?>()
    val cContent: LiveData<AspectosBasicosModel?> get() = _courseContent

    fun fetchData(documentId: String) {
        val collections = listOf("aspectosBasicos", "fundamentosDeProgramación", "herramientas")
        fetchFromCollections(collections, documentId, 0)
    }

    private fun fetchFromCollections(collections: List<String>, documentId: String, index: Int) {
        if (index >= collections.size) {
            // Si hemos revisado todas las colecciones y no encontramos el documento
            _courseContent.value = null
            Log.e("fetchData", "El documento no se encontró en ninguna colección.")
            return
        }

        val collection = collections[index]
        db.collection(collection)
            .document(documentId)
            .get()
            .addOnSuccessListener { document ->
                // Si el documento no existe, intentamos con la siguiente colección
                if (document != null) {
                    // Mapeo del documento de acuerdo al modelo
                    val usuarioData = document?.toObject<AspectosBasicosModel>()
                    _courseContent.value = usuarioData
                    Log.d("lLiveResponse:", _courseContent.value.toString())

                    if (_courseContent.value == null){
                        Log.e("fetchData", "El documento no existe en la colección: $collection")
                        fetchFromCollections(collections, documentId, index + 1)
                    }
                }
            }
            .addOnFailureListener { exception ->
                // Manejar errores
                Log.e("fetchData", "Error al obtener el documento: ${exception.message}")
                // Intentamos con la siguiente colección incluso si hay un error
                fetchFromCollections(collections, documentId, index + 1)
            }
    }
}
