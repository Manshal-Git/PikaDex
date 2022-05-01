package com.manshal_khatri.pikadex.model

import org.json.JSONArray

data class Pokemons(

    val id : Int = 0,
    val pokeName : String = "",
    val pokeType : Types = Types(),
    val spriteUrl:String ="",
    val stats : Stats = Stats(0,0,0,0,0,0,)
)
