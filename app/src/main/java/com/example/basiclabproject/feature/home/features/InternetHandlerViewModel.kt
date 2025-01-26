package com.example.basiclabproject.feature.home.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class InternetHandlerViewModel @Inject constructor() : ViewModel() {

    class ConnectivityViewModel(private val networkService: NetworkConnectivityService) : ViewModel() {
        private val _networkStatus = MutableStateFlow<NetworkStatus>(NetworkStatus.Disconnected)
        val networkStatus: StateFlow<NetworkStatus> = _networkStatus.asStateFlow()

        init {
            viewModelScope.launch {
                networkService.observeNetworkStatus().collect { status ->
                    _networkStatus.value = status
                }
            }
        }
    }

}