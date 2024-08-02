package com.example.pokemonapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {
    @GET("pokemon/{name}")
    fun getPokemon(@Path("name") name: String): Call<Pokemon>
}
