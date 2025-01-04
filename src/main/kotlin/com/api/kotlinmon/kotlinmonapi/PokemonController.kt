package com.api.kotlinmon.kotlinmonapi

import com.api.kotlinmon.kotlinmonapi.model.Ability
import com.api.kotlinmon.kotlinmonapi.model.Pokemon
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class PokemonController {
    val pokeApiClientConfig = PokeApiClientConfig()

    @GetMapping("/pokemon/{name}")
    suspend fun getPokemon(@PathVariable name: String) {
        val baseUrl = pokeApiClientConfig.rootUrl
        val client = pokeApiClientConfig.httpClient
        val response: Pokemon = client.get("$baseUrl/pokemon/$name").body()
        println(response)
        client.close() // TODO: Close client properly
    }

    @GetMapping("/ability/{name}")
    suspend fun getAbility(@PathVariable name: String) {
        val pokeApiClientConfig = PokeApiClientConfig()
        val baseUrl = pokeApiClientConfig.rootUrl
        val client = pokeApiClientConfig.httpClient
        val response: Ability = client.get("$baseUrl/ability/$name").body()
        println(response)
        client.close() // TODO: Close client properly
    }
}