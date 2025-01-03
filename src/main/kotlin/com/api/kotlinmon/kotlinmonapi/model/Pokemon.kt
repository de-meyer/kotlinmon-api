package com.api.kotlinmon.kotlinmonapi.model

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon (
    val name: String,
    val height: Int,
    val weight: Int,
    val abilities: List<PokemonAbility>
)