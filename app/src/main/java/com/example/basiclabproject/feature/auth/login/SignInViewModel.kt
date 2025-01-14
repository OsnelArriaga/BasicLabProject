package com.example.basiclabproject.feature.auth.login

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel(){

    private val _state = MutableStateFlow<SignInState>(SignInState.Nothing)
    val state = _state.asStateFlow()

    fun signIn(email: String, password: String) {
        _state.value = SignInState.Loading
        //Firebase login process
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result?.user?.let {
                        _state.value = SignInState.Success
                        return@addOnCompleteListener
                    }
                    _state.value = SignInState.Error
                } else {
                    _state.value = SignInState.Error
                }
            }
    }

    fun resetPassword(userMail: String) {
        if (!TextUtils.isEmpty(userMail)) {
            Firebase.auth.sendPasswordResetEmail(userMail)
        }
    }

}



sealed class SignInState{

    //Lista de (to do) para cada estado del inicio de sesion
    object Nothing: SignInState()
    object Loading: SignInState()
    object Success: SignInState()
    object Error: SignInState()

}
