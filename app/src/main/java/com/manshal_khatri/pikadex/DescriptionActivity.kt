package com.manshal_khatri.pikadex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import com.manshal_khatri.pikadex.databinding.ActivityDescriptionBinding
import com.manshal_khatri.pikadex.databinding.ActivityMainBinding
import com.manshal_khatri.pikadex.fragments.FirstFragment

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
        supportFragmentManager.beginTransaction().replace(R.id.desc_frag_container,FirstFragment()).commit()

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