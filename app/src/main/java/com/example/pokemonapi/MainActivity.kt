package com.example.pokemonapi

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var etPokemonName: EditText
    private lateinit var btnSearchPokemon: Button
    private lateinit var ivPokemonSprite: ImageView
    private lateinit var tvPokemonData: TextView
    private lateinit var service: PokeApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etPokemonName = findViewById(R.id.etPokemonName)
        btnSearchPokemon = findViewById(R.id.btnSearchPokemon)
        ivPokemonSprite = findViewById(R.id.ivPokemonSprite)
        tvPokemonData = findViewById(R.id.tvPokemonData)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(PokeApiService::class.java)

        btnSearchPokemon.setOnClickListener {
            val pokemonName = etPokemonName.text.toString().trim()
            if (pokemonName.isNotEmpty()) {
                searchPokemon(pokemonName)
            }
        }
        //default pokemon
        searchPokemon("snorlax")
    }

    private fun searchPokemon(name: String) {
        service.getPokemon(name).enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if (response.isSuccessful) {
                    val pokemon = response.body()
                    pokemon?.let {
                        displayPokemonData(it)
                    }
                } else {
                    tvPokemonData.text = "Pokémon not found."
                    ivPokemonSprite.setImageResource(0)
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                tvPokemonData.text = t.message
            }
        })
    }

    private fun displayPokemonData(pokemon: Pokemon) {
        Glide.with(this).load(pokemon.sprites.frontDefault).into(ivPokemonSprite)

        val data = StringBuilder()
        data.append("Name: ${pokemon.name.capitalize()}\n")
        data.append("Height: ${pokemon.height}\n")
        data.append("Weight: ${pokemon.weight}\n")
        data.append("Types: ${pokemon.types.joinToString { it.type.name.capitalize() }}\n")
        data.append("Abilities: ${pokemon.abilities.joinToString { it.ability.name.capitalize() }}\n\n")
        data.append("Stats:\n")
        pokemon.stats.forEach {
            data.append("${it.stat.name.capitalize()}: ${it.base_stat}\n")
        }

        tvPokemonData.text = data.toString()
    }
}
