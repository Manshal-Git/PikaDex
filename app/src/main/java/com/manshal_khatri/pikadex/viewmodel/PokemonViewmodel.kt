package com.manshal_khatri.pikadex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manshal_khatri.pikadex.model.Pokemons
import com.manshal_khatri.pikadex.pokemonsList

class PokemonViewmodel:ViewModel() {
    private val _vmPokemonList = MutableLiveData<MutableList<Pokemons>>(pokemonsList)
    val vmPokemonList : LiveData<MutableList<Pokemons>>
    get() = _vmPokemonList

    fun addPkmn(pkmn : Pokemons){
        _vmPokemonList.value?.add(pkmn)
    }
    fun addallPkmn(list:List<Pokemons>){
        _vmPokemonList.value?.addAll(list)
    }

}