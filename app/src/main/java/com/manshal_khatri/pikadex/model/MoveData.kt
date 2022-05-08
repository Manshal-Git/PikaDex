package com.manshal_khatri.pikadex.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "move")
data class MoveData(
    val mid : Int,
    @PrimaryKey
    val name : String = "",
    val power : Int? = -1,
    val accuracy : Int? = 100,
    val pp : Int? = 25,
    val type : String ="",
    val kind : String =""
)
