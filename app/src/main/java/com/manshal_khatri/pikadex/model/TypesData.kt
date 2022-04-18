package com.manshal_khatri.pikadex.model

import android.graphics.drawable.ShapeDrawable

data class TypesData(
    val id : Int,
    val name : String,
    val advantage : List<String>,
    val disAdvantage : List<String>,
    val immune : List<String>
//    val drawable: ShapeDrawable
)
