package com.api.kotlinmon.kotlinmonapi.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonAbility (
    val is_hidden: Boolean,
    val slot: Int,
    val ability: Ability
    )