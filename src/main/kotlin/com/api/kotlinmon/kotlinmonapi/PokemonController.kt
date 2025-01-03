package com.api.kotlinmon.kotlinmonapi

import com.api.kotlinmon.kotlinmonapi.model.Pokemon
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pokemon")
class PokemonController {
    @GetMapping("/{name}")
    suspend fun getPokemon(@PathVariable name: String) {
        val pokeApiClientConfig = PokeApiClientConfig()
        val baseUrl = pokeApiClientConfig.rootUrl
        val client = pokeApiClientConfig.httpClient
        val response: Pokemon = client.get("$baseUrl/pokemon/$name").body()
        println(response)
        client.close()
    }
}