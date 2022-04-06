package com.manshal_khatri.pikadex.model

import kotlinx.coroutines.Deferred

data class MoveData(
    val mid : Int,
    val power : Int,
    val accuracy : Int,
    val pp : Int,
    val type : String,
    val kind : String
)
