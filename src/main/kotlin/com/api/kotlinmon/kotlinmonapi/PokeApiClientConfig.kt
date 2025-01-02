package com.api.kotlinmon.kotlinmonapi

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class PokeApiClientConfig(
    val rootUrl: String = "https://pokeapi.co/api/v2",
    val httpClient: HttpClient = HttpClient(CIO) {

        engine {
            requestTimeout = 5000
        }
        expectSuccess = true

    }
)

