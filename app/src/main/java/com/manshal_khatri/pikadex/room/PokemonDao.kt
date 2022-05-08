package com.manshal_khatri.pikadex.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manshal_khatri.pikadex.model.Pokemons

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun storePokemon(pkmn : Pokemons)

    @Query("select * from Pokemon")
    fun getAllPokemon(): LiveData<List<Pokemons>>

    @Query("select count(id) from Pokemon")
    suspend fun isUpToDate():Int

}