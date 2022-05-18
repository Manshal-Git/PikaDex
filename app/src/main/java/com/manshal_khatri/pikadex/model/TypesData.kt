package com.manshal_khatri.pikadex.model

import android.graphics.drawable.ShapeDrawable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.manshal_khatri.pikadex.util.Convertors

@Entity(tableName = "type")
//@TypeConverters(Convertors::class)
data class TypesData(
    @PrimaryKey
    val id : Int,
    val name : String,
    val advantage : List<String>,
    val disAdvantage : List<String>,
    val immune : List<String>
//    val drawable: ShapeDrawable
)
