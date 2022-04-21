package com.manshal_khatri.pikadex.model

data class MoveData(
    val mid : Int,
    val name : String = "",
    val power : Int? = -1,
    val accuracy : Int? = 100,
    val pp : Int? = 25,
    val type : String ="",
    val kind : String =""
)
