package com.zameenmitra.client

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*

class ZameenMitraApiClient {
    private val client = HttpClient()

    // Real multipart form data upload
    suspend fun uploadDocument(fileBytes: ByteArray, fileName: String): String {
        val response: HttpResponse = client.submitFormWithBinaryData(
            url = "http://10.0.2.2:8080/api/v1/vault/upload", // 10.0.2.2 is localhost for Android emulator
            formData = formData {
                append("document", fileBytes, Headers.build {
                    append(HttpHeaders.ContentType, "application/octet-stream")
                    append(HttpHeaders.ContentDisposition, "filename=\"$fileName\"")
                })
            }
        )
        return response.bodyAsText()
    }
}
