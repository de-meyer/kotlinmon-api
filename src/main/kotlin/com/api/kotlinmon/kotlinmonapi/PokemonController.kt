package com.api.kotlinmon.kotlinmonapi

import com.api.kotlinmon.kotlinmonapi.exceptions.ResourceNotFoundException
import com.api.kotlinmon.kotlinmonapi.models.Ability
import com.api.kotlinmon.kotlinmonapi.models.Pokemon
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

    @GetMapping("/pokemon/{name}")
    suspend fun getPokemon(@PathVariable name: String) {
        try {
            val response: Pokemon = client.get("$baseUrl/pokemon/$name").body()
            println(response)
        } catch (ex: Exception) {
            if (ex is ClientRequestException && ex.response.status.value == 404) {
                throw ResourceNotFoundException("Pokemon not found with name/id: $name")
            } else {
                throw ex
            }
        }
    }

    @GetMapping("/ability/{name}")
    suspend fun getAbility(@PathVariable name: String) {
        try {
            val response: Ability = client.get("$baseUrl/ability/$name").body()
            println(response)
        } catch (ex: Exception) {
            if (ex is ClientRequestException && ex.response.status.value == 404) {
                throw ResourceNotFoundException("Ability not found with name/id: $name")
            } else {
                throw ex
            }
        }
    }
}