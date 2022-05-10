package com.manshal_khatri.pikadex.util

import android.app.Dialog
import android.content.Context
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.appcompat.app.AppCompatViewInflater
import androidx.appcompat.widget.AppCompatImageView
import com.manshal_khatri.pikadex.R

class ContentMaker(context: Context) : AppCompatDialogFragment() {

    fun loadingScreen() : AppCompatDialogFragment {
        val dialog = AppCompatDialogFragment()


        return dialog
    }
    fun toggleDialog(dialog: Dialog){
        if(dialog.isShowing){
            dialog.hide()
        }else{
            dialog.show()
        }
    }

}