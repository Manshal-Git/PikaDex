package com.manshal_khatri.pikadex.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pokemon")
data class Pokemons(
    val id : Int = 0,
    @PrimaryKey val pokeName : String = "",
    val pokeType : PokeTypes = PokeTypes(),
    val spriteUrl:String ="",
    val stats : Stats = Stats()
)
