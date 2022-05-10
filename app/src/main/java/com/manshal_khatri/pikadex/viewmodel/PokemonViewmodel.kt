package com.manshal_khatri.pikadex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manshal_khatri.pikadex.evolutionChain
import com.manshal_khatri.pikadex.model.Pokemons
import com.manshal_khatri.pikadex.pokemonsList

class PokemonViewmodel:ViewModel() {
    private val _vmPokemonList = MutableLiveData<MutableList<Pokemons>>(pokemonsList)
    private val _vmEvolutionChain = MutableLiveData<MutableList<String>>(evolutionChain)
    val vmPokemonList : LiveData<MutableList<Pokemons>>
    get() = _vmPokemonList
    val vmEvolutionChain : LiveData<MutableList<String>>
    get() = _vmEvolutionChain

    fun addToEvolutionChain(pkmnName : String){
        _vmEvolutionChain.value?.add(pkmnName)
        _vmEvolutionChain.value = _vmEvolutionChain.value
    }

    fun clearEvolutionChain() = _vmEvolutionChain.value?.clear()

    fun addPkmn(pkmn : Pokemons){
        _vmPokemonList.value?.add(pkmn)
        _vmPokemonList.value = _vmPokemonList.value
    }
    fun addallPkmn(list:List<Pokemons>){
        _vmPokemonList.value?.addAll(list)
    }

    fun clearAllPkmn() = _vmPokemonList.value?.clear()
}