package com.api.kotlinmon.kotlinmonapi

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class PokeApiClientConfig(
    val rootUrl: String = "https://pokeapi.co/api/v2",
    val httpClient: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true }) // Configure JSON handling
        }
        engine {
            requestTimeout = 5000
        }
        expectSuccess = true

    }
)

