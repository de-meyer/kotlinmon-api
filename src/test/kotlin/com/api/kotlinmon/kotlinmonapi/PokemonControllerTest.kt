package com.api.kotlinmon.kotlinmonapi

import com.api.kotlinmon.kotlinmonapi.exceptions.ResourceNotFoundException
import com.api.kotlinmon.kotlinmonapi.models.Pokemon
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
@WebMvcTest(PokemonController::class)
class PokemonControllerTest {
    @Autowired
    lateinit var pokemonController: PokemonController

    @Test
    fun shouldReturnPokemonByName() = runBlocking {
        val pokemon: Pokemon = pokemonController.getPokemon("pikachu")
        assertEquals("pikachu", pokemon.name)
    }

    @Test
    fun shouldReturnPokemonById() = runBlocking {
        val pokemon: Pokemon = pokemonController.getPokemon("25")
        assertEquals("pikachu", pokemon.name)
    }

    @Test
    fun shouldReturnPokemonByInvalidName() = runBlocking {
        val exception = assertThrows<ResourceNotFoundException> {
            pokemonController.getPokemon("invalid")
        }
        assertEquals("Pokemon not found with name/id: invalid", exception.message)
    }

}