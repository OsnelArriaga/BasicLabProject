package com.example.basiclabproject.feature.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.basiclabproject.models.CardInfo
import com.example.basiclabproject.navigation.Screens
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.tasks.await


@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val db = Firebase.firestore


    private val _cardInfo = mutableStateListOf<CardInfo>()
    val cardInfo: List<CardInfo> get() = _cardInfo

    private val _isLoading = mutableStateOf(true)
    val isLoading: Boolean get() = _isLoading.value

    init {
        getCardInfo()
    }

    private fun getCardInfo() {
        viewModelScope.launch {
            try {
                val result = db.collection("aspectosBasicos")
                    .get()
                    .await()
                val userList = result.map { document ->
                    document.toObject(CardInfo::class.java).copy(id = document.id)
                }
                _cardInfo.addAll(userList)
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



//    private val _firestoreData = mutableStateListOf<FirestoreData>()
//    val firestoreData: List<FirestoreData> by _firestoreData
//
//
//    private val _isLoading = mutableStateOf(false)
//    val isLoading = mutableStateOf(_isLoading)
//
//    public fun fetchData() {
////        _isLoading.value = true
//        db.collection("aspectosBasicos")
//            .get()
//            .addOnSuccessListener { querySnapshot ->
//                val updatedList = _firestoreData.value.toMutableList() // Create a mutable copy
//                querySnapshot.documents.forEach { document ->
//                    val data = document.toObject(FirestoreData::class.java)
//                    data?.let { updatedList.add(it) } // Add to the mutable copy
//                }
//                _firestoreData.value = updatedList // Update the state with the new list
////                _isLoading.value = false
//            }
//    }
//}
//
//data class FirestoreData(
//    val id: String = "",
//    val titulo: String = "",
//    // ... otros campos de tu colección
//)


//    private fun getAEModel(){
//        firebaseDatabase.getReference("aspectosEscenciales")
//            .get()
//            .addOnSuccessListener{
//                val list = mutableListOf<AspectosEscencialesModel>()
//                it.children.forEach { data ->
//                    val aemodel = AspectosEscencialesModel(data.key!!, data.value.toString())
//                    list.add(aemodel)
//                }
//            _AspectosEscencialesModel.value = list
//        }
//    }


//    suspend fun getDataFromFirestore(): About {
//
//    val db = FirebaseFirestore.getInstance()
//    var about = About()
//
//    try {
//        db.collection("aspectosBasicos")
//            .get()
//            .addOnSuccessListener { result ->
//
//                for (document in result) {
//                    about = document.toObject(About::class.java)
//                    Log.d("Firestore", "${document.id} => ${document.data}")
//
//                }
//            }
//    } catch (e: FirebaseFirestoreException) {
//        Log.d("Error", e.toString())
//
//    }
//
//    return about
//}
