package com.zameenmitra.client

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Represents the state of the UI
sealed class UploadState {
    object Idle : UploadState()
    object Loading : UploadState()
    data class Success(val response: VaultUploadResponse) : UploadState()
    data class Error(val message: String) : UploadState()
}

class VaultViewModel {
    private val apiClient = ZameenMitraApiClient()
    
    // Use Dispatchers.Default for shared CoroutineScope (works on iOS and Android)
    private val scope = CoroutineScope(Dispatchers.Default)

    private val _uploadState = MutableStateFlow<UploadState>(UploadState.Idle)
    val uploadState: StateFlow<UploadState> = _uploadState.asStateFlow()

    fun uploadFile(fileBytes: ByteArray, fileName: String) {
        scope.launch {
            _uploadState.value = UploadState.Loading
            try {
                val result = apiClient.uploadDocument(fileBytes, fileName)
                _uploadState.value = UploadState.Success(result)
            } catch (e: Exception) {
                _uploadState.value = UploadState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
    
    fun resetState() {
        _uploadState.value = UploadState.Idle
    }
}
