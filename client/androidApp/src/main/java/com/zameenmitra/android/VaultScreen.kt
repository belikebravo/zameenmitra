package com.zameenmitra.android

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zameenmitra.client.UploadState
import com.zameenmitra.client.VaultViewModel

@Composable
fun VaultScreen() {
    // In a real app, you would use a proper ViewModel lifecycle or Koin injection
    // For POC, we instantiate the shared ViewModel directly.
    val viewModel = remember { VaultViewModel() }
    val uploadState by viewModel.uploadState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "ZameenMitra Property Vault",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(32.dp))

        when (val state = uploadState) {
            is UploadState.Idle -> {
                Button(onClick = {
                    // Simulating a file selection (byte array)
                    val mockFileBytes = "Fake PDF Content".toByteArray()
                    viewModel.uploadFile(mockFileBytes, "sale_deed_mock.pdf")
                }) {
                    Text("Upload Property Document")
                }
            }
            is UploadState.Loading -> {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Uploading and Verifying...")
            }
            is UploadState.Success -> {
                Text(
                    text = "Upload Complete!",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Server Response: ${state.response}")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.resetState() }) {
                    Text("Upload Another Document")
                }
            }
            is UploadState.Error -> {
                Text(
                    text = "Upload Failed",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = state.message, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.resetState() }) {
                    Text("Try Again")
                }
            }
        }
    }
}
