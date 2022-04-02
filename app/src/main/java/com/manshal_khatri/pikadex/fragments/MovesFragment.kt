package com.manshal_khatri.pikadex.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.manshal_khatri.pikadex.R

/**
 * A simple [Fragment] subclass.
 * Use the [MovesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_moves, container, false)



        return view
    }

}