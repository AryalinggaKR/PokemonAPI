package com.example.pokemonapi

import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("name") val name: String,
    @SerializedName("height") val height: Int,
    @SerializedName("weight") val weight: Int,
    @SerializedName("types") val types: List<Type>,
    @SerializedName("abilities") val abilities: List<Ability>,
    @SerializedName("stats") val stats: List<Stat>,
    @SerializedName("sprites") val sprites: Sprites
)

data class Type(
    @SerializedName("slot") val slot: Int,
    @SerializedName("type") val type: TypeDetails
)

data class TypeDetails(
    @SerializedName("name") val name: String
)

data class Ability(
    @SerializedName("ability") val ability: AbilityDetails
)

data class AbilityDetails(
    @SerializedName("name") val name: String
)

data class Stat(
    @SerializedName("base_stat") val base_stat: Int,
    @SerializedName("stat") val stat: StatDetails
)

data class StatDetails(
    @SerializedName("name") val name: String
)

data class Sprites(
    @SerializedName("front_default") val frontDefault: String
)
