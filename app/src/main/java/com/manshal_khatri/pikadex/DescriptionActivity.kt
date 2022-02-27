package com.manshal_khatri.pikadex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.manshal_khatri.pikadex.databinding.ActivityDescriptionBinding
import com.manshal_khatri.pikadex.databinding.ActivityMainBinding

class DescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

//        val actionBar = actionBar
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar  = binding.collaspingToolbar
        val appbar  = binding.appBar
        appbar.verticalFadingEdgeLength

//        println("Working")
//        println(toolbar.verticalFadingEdgeLength)
//        if(toolbar.isHorizontalFadingEdgeEnabled) {
//            if (toolbar.verticalFadingEdgeLength < 56) {
//                binding.pokeName.visibility = GONE
//                println(toolbar.verticalFadingEdgeLength)
//            } else {
//                binding.pokeName.visibility = VISIBLE
//                println(toolbar.verticalFadingEdgeLength)
//            }
//        }

    }
}