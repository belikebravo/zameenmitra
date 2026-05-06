package com.zameenmitra.client

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class PropertyRecord(
    val state: String,
    val district: String,
    val village: String,
    val survey_number: String,
    val document_name: String
)

@Serializable
data class GovtValidationResult(
    val is_verified: Boolean,
    val registered_owner: String,
    val plot_area_sq_ft: Int,
    val encumbrance_status: String,
    val api_source: String
)

@Serializable
data class VaultUploadResponse(
    val message: String,
    val document_id: String,
    val original_name: String,
    val status: String,
    val extracted_record: PropertyRecord? = null,
    val validation_result: GovtValidationResult? = null
)

class ZameenMitraApiClient {
    private val client = HttpClient()

    // Real multipart form data upload
    suspend fun uploadDocument(fileBytes: ByteArray, fileName: String): VaultUploadResponse {
        val response: HttpResponse = client.submitFormWithBinaryData(
            url = "http://10.0.2.2:8080/api/v1/vault/upload", // 10.0.2.2 is localhost for Android emulator
            formData = formData {
                append("document", fileBytes, Headers.build {
                    append(HttpHeaders.ContentType, "application/octet-stream")
                    append(HttpHeaders.ContentDisposition, "filename=\"$fileName\"")
                })
            }
        )
        
        val jsonString = response.bodyAsText()
        val format = Json { ignoreUnknownKeys = true }
        return format.decodeFromString<VaultUploadResponse>(jsonString)
    }
}
