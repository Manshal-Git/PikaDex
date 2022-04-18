package com.manshal_khatri.pikadex.model

import kotlinx.coroutines.Deferred

data class Moves(
    val mid : Int,
    val learningLvl: Int,
    val name: String,
    val data : MoveData
//    val url : String
)
