package com.manshal_khatri.pikadex.util

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class PkmnNameTitle(context: Context,attrs : AttributeSet) : AppCompatTextView(context , attrs) {
    init {
        applyFont()
    }
    private fun applyFont(){
//        val titleFonts : Typeface= Typeface.createFromAsset(context.assets,"sans-serif-condensed")
//        typeface=titleFonts

    }
}