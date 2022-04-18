package com.manshal_khatri.pikadex.model

import kotlinx.coroutines.Deferred

data class MoveData(
    val mid : Int,
    val power : Int = 100,
    val accuracy : Int = 100,
    val pp : Int = 25,
    val type : String ="",
    val kind : String =""
)
