package com.api.kotlinmon.kotlinmonapi

import io.ktor.client.request.*
import io.ktor.client.statement.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class PokemonController {
    @GetMapping("/pokemon")
    suspend fun getPokemon(){
        val pokeApiClientConfig = PokeApiClientConfig()
        val baseUrl = pokeApiClientConfig.rootUrl
        val client = pokeApiClientConfig.httpClient
        val response: HttpResponse = client.get("$baseUrl/pokemon/ditto")
        println(response.bodyAsText())
        client.close()
        println("Getting Pokemon")
    }
}