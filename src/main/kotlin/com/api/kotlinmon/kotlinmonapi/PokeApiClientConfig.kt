package com.api.kotlinmon.kotlinmonapi

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "pokeapi")
class PokeApiClientConfig(
    var rootUrl: String = "https://example.com/api",
    var requestTimeout: Long = 10000L,
    var ignoreUnknownKeys: Boolean = true,
) {

    // Lazily initialized HttpClient
    val httpClient: HttpClient by lazy {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json { this.ignoreUnknownKeys = this@PokeApiClientConfig.ignoreUnknownKeys })
            }
            engine {
                this.requestTimeout = this@PokeApiClientConfig.requestTimeout
            }
            expectSuccess = true
        }
    }
}

