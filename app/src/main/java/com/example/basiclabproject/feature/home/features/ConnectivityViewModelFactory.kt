package com.example.basiclabproject.feature.home.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ConnectivityViewModelFactory(private val networkService: NetworkConnectivityService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InternetHandlerViewModel.ConnectivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InternetHandlerViewModel.ConnectivityViewModel(networkService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}