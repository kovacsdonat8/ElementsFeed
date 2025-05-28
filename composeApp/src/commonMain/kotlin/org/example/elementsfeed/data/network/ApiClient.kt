package org.example.elementsfeed.data.network

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.elementsfeed.data.model.ProductsResponse

class ApiClient {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    suspend fun getProduct(skip: Int): Result<ProductsResponse> {
        return try {
            val response: ProductsResponse = httpClient.get("https://dummyjson.com/products") {
                parameter("limit", 1)
                parameter("skip", skip)
            }.body()
            println("Response: $response")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun close() {
        httpClient.close()
    }
}