package com.api.kotlinmon.kotlinmonapi

import com.api.kotlinmon.kotlinmonapi.exceptions.ResourceNotFoundException
import com.api.kotlinmon.kotlinmonapi.models.Ability
import com.api.kotlinmon.kotlinmonapi.models.Pokemon
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import io.github.resilience4j.kotlin.ratelimiter.executeSuspendFunction
import io.github.resilience4j.kotlin.retry.executeSuspendFunction
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class PokemonController {
    private final val config = PokeApiClientConfig(
        rootUrl = "https://pokeapi.co/api/v2",
        requestTimeout = 10000L
    )

    private val client = config.httpClient
    private val baseUrl = config.rootUrl
    private val rateLimiter = config.rateLimiter
    private val circuitBreaker = config.circuitBreaker
    private val retry = config.retry
    @GetMapping("/pokemon/{name}")
    suspend fun getPokemon(@PathVariable name: String): Pokemon {
        try {
           return retry.executeSuspendFunction {
                circuitBreaker.executeSuspendFunction {
                    rateLimiter.executeSuspendFunction {
                        client.get("$baseUrl/pokemon/$name").body<Pokemon>()
                    }
                }
            }
        }catch (ex: Exception) {
            if (ex is ClientRequestException && ex.response.status.value == 404) {
                throw ResourceNotFoundException("Pokemon not found with name/id: $name")
            } else {
                throw ex
            }
        }
    }

    @GetMapping("/ability/{name}")
    suspend fun getAbility(@PathVariable name: String): Ability {
        try {
           return client.get("$baseUrl/ability/$name").body()
        } catch (ex: Exception) {
            if (ex is ClientRequestException && ex.response.status.value == 404) {
                throw ResourceNotFoundException("Ability not found with name/id: $name")
            } else {
                throw ex
            }
        }
    }
}