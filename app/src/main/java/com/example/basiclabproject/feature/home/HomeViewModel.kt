package com.example.basiclabproject.feature.home

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.basiclabproject.models.AspectosBasicosModel
import com.example.basiclabproject.models.CursosVisitadosModel
import com.example.basiclabproject.models.FundamentosModel
import com.example.basiclabproject.models.HerramientasModel
import com.example.basiclabproject.navigation.Screens
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.tasks.await
import java.util.UUID


@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    //BASE DE DATOS ESTANDAR
    private val db = Firebase.firestore

    //BASE DE DATOS EN TIEMPO REAL
    private val dbRealtime = Firebase.database

    private val _aspectosBasicoModels = mutableStateListOf<AspectosBasicosModel>()
    val aspectosBasicosModel: List<AspectosBasicosModel> get() = _aspectosBasicoModels

    private val _fundamentosModels = mutableStateListOf<FundamentosModel>()
    val fundamentosModels: List<FundamentosModel> get() = _fundamentosModels

    private val _herrramientasModels = mutableStateListOf<HerramientasModel>()
    val herrramientasModels: List<HerramientasModel> get() = _herrramientasModels

    private val _isLoading = mutableStateOf(true)
    val isLoading: Boolean get() = _isLoading.value

    init {
        aspectosBasicosConsulta()
        fundamentosConsulta()
        herramientasConsulta()
    }

    //CONSULTA PARA ACTUALIZAR DATOS AL REFRESCAR
    fun loadData() {
        viewModelScope.launch {
            if(_aspectosBasicoModels != null && _fundamentosModels != null && _herrramientasModels != null)run {
                _aspectosBasicoModels.clear()
                _herrramientasModels.clear()
                _fundamentosModels.clear()
            }
            aspectosBasicosConsulta()
            fundamentosConsulta()
            herramientasConsulta()
        }
    }

    private val _courseContent = MutableLiveData<AspectosBasicosModel?>()
    val cContent: LiveData<AspectosBasicosModel?> get() = _courseContent

    fun busqueda(documentId: String) {
        val collections = listOf("aspectosBasicos", "fundamentosDeProgramación", "herramientas")
        fetchFromCollections(collections, documentId, 0)
    }

    private fun fetchFromCollections(collections: List<String>, documentId: String, index: Int) {
        if (index >= collections.size) {
            // Si hemos revisado todas las colecciones y no encontramos el documento
            _courseContent.value = null
            Log.e("buscador", "El documento no se encontró en ninguna colección.")
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
                    Log.d("buscador:", _courseContent.value.toString())

                    if (_courseContent.value == null){
                        Log.e("fetchData", "El documento no existe en la colección: $collection")
                        fetchFromCollections(collections, documentId, index + 1)
                    }
                }
            }
            .addOnFailureListener { exception ->
                // Manejar errores
                Log.e("buscador", "Error al obtener el documento: ${exception.message}")
                // Intentamos con la siguiente colección incluso si hay un error
                fetchFromCollections(collections, documentId, index + 1)
            }
    }

    fun aspectosBasicosConsulta() {
        viewModelScope.launch {
            try {
                val result = db.collection("aspectosBasicos")
                    .get()
                    .await()
                val userList = result.map { document ->
                    document.toObject(AspectosBasicosModel::class.java).copy(id = document.id)
                }
                _aspectosBasicoModels.addAll(userList)
            } catch (e: Exception) {
                // Handle the error
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fundamentosConsulta() {
        viewModelScope.launch {
            try {
                val result = db.collection("fundamentosDeProgramación")
                    .get()
                    .await()
                val fundamentosResult = result.map { document ->
                    document.toObject(FundamentosModel::class.java).copy(id = document.id)
                }
                _fundamentosModels.addAll(fundamentosResult)
            } catch (e: Exception) {
                // Handle the error
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun herramientasConsulta() {
        viewModelScope.launch {
            try {
                val result = db.collection("herramientas")
                    .get()
                    .await()
                val herramientasResult = result.map { document ->
                    document.toObject(HerramientasModel::class.java).copy(id = document.id)
                }
                _herrramientasModels.addAll(herramientasResult)
            } catch (e: Exception) {
                // Handle the error
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logoutUser(navController: NavController) {
        Firebase.auth.signOut()
        // Limpiar datos de sesión locales
        navController.navigate(Screens.LoginScreen.route)
    }

    fun guardarCurso(
        courseID: String,
        tituloCurso: String,
        topicos: List<String>,
        dificultad: String
    ) {

        val userId = Firebase.auth.currentUser?.uid ?: return // OBTENER EL USUARIO DEL FBAUTH

        // Referencia a la ubicación donde se guardan los cursos visitados
        val userCoursesRef = dbRealtime.getReference("usuarios")
            .child(userId)
            .child("leccionesVisitadas")
            .child(courseID)

        // Verificar si el curso ya ha sido visitado
        userCoursesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // El curso ya ha sido visitado, no hacer nada
                    Log.d("Firebase", "La leccion ya ha sido visitada: $courseID")
                } else {
                    // Crear un nuevo título de curso
                    val titleEntry = CursosVisitadosModel(
                        id = UUID.randomUUID().toString(), // Usar courseID como clave
                        userId,
                        courseID,
                        tituloCurso,
                        System.currentTimeMillis(),
                        topicos,
                        dificultad
                    )
                    // Guardar el título en la ruta específica del usuario
                    userCoursesRef
                        .push()
                        .setValue(titleEntry)
                        .addOnSuccessListener {
                            Log.d("Firebase", "Leccion guardada exitosamente: $courseID")
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firebase", "Error al guardar la leccion", e)
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Firebase", "Error al verificar la leccion", error.toException())
            }
        })
    }


    val userId = Firebase.auth.currentUser?.uid!!

    private val _dataFromFirebase = MutableStateFlow<Map<String, Any>>(emptyMap())
    val dataFromFirebase: StateFlow<Map<String, Any>> = _dataFromFirebase.asStateFlow()

    fun leerDatosDelPadre(onResult: (Map<String, Any>) -> Unit) {
        val database: DatabaseReference = FirebaseDatabase.getInstance()
            .getReference("usuarios")
            .child(userId)
            .child("leccionesVisitadas")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = mutableMapOf<String, Any>()
                for (snapshot in dataSnapshot.children) {
                    data[snapshot.key ?: ""] = snapshot.value ?: ""
                }
                onResult(data) // Accede al campo específico


                _dataFromFirebase.value = data
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar el error
                Log.w("Firebase", "Error al leer los datos.", databaseError.toException())
                onResult(emptyMap()) // Retornar un mapa vacío en caso de error
            }
        })
    }

    fun borrarLeccionEspecifica(eLeccionVisitada: String) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("usuarios")
            .child(userId)
            .child("leccionesVisitadas")
            .child(eLeccionVisitada)

        // Borra la lección específica
        myRef.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.w("FirebaseEliminar", "Borrada Exitosamente: $eLeccionVisitada")
            } else {
                // Manejo de errores
                Log.w("FirebaseEliminar", "Error al borrar la lección: ${task.exception?.message}")
            }
        }
    }
}