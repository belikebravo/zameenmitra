package com.zameenmitra.client

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class ZameenMitraApiClient {
    private val client = HttpClient()

    suspend fun uploadDocument(fileBytes: ByteArray, fileName: String): String {
        // Stub for POC: Sending bytes to the backend
        val response: HttpResponse = client.post("http://localhost:3000/api/v1/vault/upload") {
            // Note: Real implementation will use MultiPartFormDataContent
            setBody(fileBytes)
        }
        return response.bodyAsText()
    }
}
