package com.api.kotlinmon.kotlinmonapi

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.ratelimiter.RateLimiter
import io.github.resilience4j.ratelimiter.RateLimiterConfig
import io.github.resilience4j.retry.Retry
import io.github.resilience4j.retry.RetryConfig
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.time.Duration

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

    // Resilience4j configurations
    val circuitBreaker: CircuitBreaker = CircuitBreaker.of(
        "pokeapiCircuitBreaker",
        CircuitBreakerConfig.custom()
            .failureRateThreshold(50F)
            .waitDurationInOpenState(Duration.ofSeconds(10))
            .slidingWindowSize(5)
            .build()
    )

    val rateLimiter: RateLimiter = RateLimiter.of(
        "pokeapiRateLimiter",
        RateLimiterConfig.custom()
            .timeoutDuration(Duration.ofMillis(100))
            .limitForPeriod(5) // Max 5 requests per second
            .limitRefreshPeriod(Duration.ofSeconds(30))
            .build()
    )
    private val retryConfig: RetryConfig = RetryConfig.custom<String>()
        .maxAttempts(3)
        .waitDuration(Duration.ofMillis(500))
        .build()

     val retry: Retry = Retry.of("pokeapiRetry", retryConfig)


}

