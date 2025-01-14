package com.example.basiclabproject.feature.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.tasks.await
import java.util.UUID


@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val db = Firebase.firestore

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

    private fun aspectosBasicosConsulta() {
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

    private fun fundamentosConsulta() {
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

    private fun herramientasConsulta() {
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

    //Guardar curso Realtime Database
    private val dbRealtime = Firebase.database

//    fun saveMessage(courseID: String, tituloCurso: String){
//
//        val userId = Firebase.auth.currentUser ?.uid ?: return // Asegúrate de que el usuario esté autenticado
//
//
//        val message = CursosVisitadosModel(
//            dbRealtime.reference.push().key?: UUID.randomUUID().toString(),
//            userId,
//            courseID,
//            tituloCurso,
//            System.currentTimeMillis(),
//        )
//
//        val key = dbRealtime.getReference("usuarios")
//            .child(userId)
//            .child("cursosVisitados")
//            .child(courseID)
//            .push()
//            .setValue(message)
//
//        Log.w("Firebase", "Key: $key")
//
//    }

    fun guardarCurso(courseID: String, tituloCurso: String, topicos: List<String>, dificultad: String) {

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

    private val _specificField =
        MutableStateFlow<String?>(null) // Use String? if the field might be null
    val specificField: StateFlow<String?> = _specificField.asStateFlow()

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


//    fun guardarCursoVisitado(cursoId: String) {
//
//        val db = FirebaseFirestore.getInstance()
//        val usuarioId = Firebase.auth.currentUser?.uid // Cambia esto por el ID del usuario actual
//
//        if (usuarioId != null) {
//            db.collection("usuarios").document(usuarioId)
//                .collection("cursosVisitados").document(cursoId)
//                .set(mapOf("id" to cursoId))
//                .addOnSuccessListener {
//                    // Curso guardado exitosamente
//                    Log.d("Firestore", "Curso guardado exitosamente: $cursoId")
//                    // Aquí puedes actualizar la UI o mostrar un mensaje al usuario
//                    //Toast.makeText(, "Curso guardado exitosamente", Toast.LENGTH_SHORT).show()
//                }
//                .addOnFailureListener { e ->
//                    // Manejar el error
//                    Log.w("Firestore", "Error al guardar el curso", e)
//                }
//        }
//    }

//    fun obtenerCursosVisitados(onResult: (List<CardInfo>) -> Unit) {
//        val db = FirebaseFirestore.getInstance()
//        val usuarioId = Firebase.auth.currentUser?.uid //
//
//        if (usuarioId != null) {
//            db.collection("usuarios").document(usuarioId)
//                .collection("cursosVisitados")
//                .get()
//                .addOnSuccessListener { documents ->
//                    val cursos = documents.map { doc ->
//                        CardInfo(doc.id, doc.getString("titulo") ?: "Sin título")
//                    }
//    //                onResult(CardInfo)
//                }
//                .addOnFailureListener { e ->
//                    // Manejar el error
//                    onResult(emptyList())
//                }
//        }
//    }


}


//    private fun getData(){
//        viewModelScope.launch {
//            state.value = getDataFromFirestore()
//        }
//    }

//    crear una lista para el contenido de los canales
//    private val _channels = MutableStateFlow<List<Channel>>(emptyList())
//    private val _AspectosEscencialesModel = MutableStateFlow<List<AspectosEscencialesModel>>(emptyList())
//
//    val channels = _channels.asStateFlow()
//    val aeModel = _AspectosEscencialesModel.asStateFlow()