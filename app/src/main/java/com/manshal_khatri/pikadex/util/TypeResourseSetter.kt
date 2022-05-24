package com.manshal_khatri.pikadex.util

import android.annotation.SuppressLint
import androidx.core.content.res.ResourcesCompat.getColor
import com.google.android.material.color.MaterialColors.getColor
import com.manshal_khatri.pikadex.R

class TypeResourseSetter {
    @SuppressLint("ResourceAsColor")
    fun setTypecolor(type:String) :  Int{
       return when(type){
            "grass" -> R.drawable.type_bg_grass
            "poison" -> (R.drawable.type_bg_poison)
            "fire" -> (R.drawable.type_bg_fire)
            "water" -> (R.drawable.type_bg_water)
            "electric" ->(R.drawable.type_bg_electric)
            "psychic" ->(R.drawable.type_bg_psychic)
            "ghost"-> (R.drawable.type_bg_ghost)
            "flying"-> (R.drawable.type_bg_flying)
            "normal"->R.drawable.type_bg_normal
            "fairy" -> R.drawable.type_bg_fairy
            "fighting" ->(R.drawable.type_bg_fighting)
            "ground" -> (R.drawable.type_bg_ground)
            "rock" -> (R.drawable.type_bg_rock)
            "bug" -> (R.drawable.type_bg_bug)
            "steel" ->(R.drawable.type_bg_steel)
            "ice" -> (R.drawable.type_bg_ice)
            "dragon" -> (R.drawable.type_bg_dragon)
            "dark" -> (R.drawable.type_bg_dark)
           else -> R.drawable.type_bg_normal
       }
    }
    fun setTypeBG(type: String): Int{
       return when(type){
            "grass" ->  (R.drawable.grass_wp)
            "poison" -> (R.drawable.posion_wp)
            "fire" -> (R.drawable.fire_wp)
            "water" -> (R.drawable.water_wp)
            "electric" ->(R.drawable.electric_wp)
            "psychic" -> (R.drawable.psychic_wp)
            "flying"-> (R.drawable.flying_wp)
            "ghost"-> (R.drawable.ghost_wp)
            "normal"-> (R.drawable.normal_wp)
            "fairy" -> (R.drawable.fairy_wp)
            "fighting" -> (R.drawable.fighting_wp)
            "ground" -> (R.drawable.ground_wp)
            "rock" -> (R.drawable.rock_wp)
            "bug" -> (R.drawable.bug_wp)
            "steel" -> (R.drawable.steel_wp)
            "ice" -> (R.drawable.ice_wp)
            "dragon" -> (R.drawable.dragon_wp)
            "dark" -> (R.drawable.dark_wp)
           else -> R.drawable.normal_wp
       }
    }
    fun setTypeTextColor(s: String) : Int {
        return when (s) {
            "flying" -> (R.color.black)
            "normal" -> (R.color.black)
            "psychic" -> R.color.black
            "ice" -> R.color.ruff_black
            else -> R.color.white
        }
    }
    fun capitalize(s: String) : String{
        return s.replaceFirst(s.first(),s.first().uppercaseChar())
    }
}