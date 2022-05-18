package com.manshal_khatri.pikadex.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "evo_chain")
//@TypeConverters(EvoConvertor::class)
data class EvolutionChainObj(
    @PrimaryKey
    val id : String = "0",
    val pokeNames : List<String>
)
