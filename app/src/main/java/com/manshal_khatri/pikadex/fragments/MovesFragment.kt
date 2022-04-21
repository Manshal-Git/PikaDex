package com.manshal_khatri.pikadex.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manshal_khatri.pikadex.R
import com.manshal_khatri.pikadex.pokeMoveData
import com.manshal_khatri.pikadex.pokeMoves
import com.manshal_khatri.pikadex.util.MovesAdapter

class MovesFragment : Fragment() {

    lateinit var movesRV : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_moves, container, false)
        movesRV = view.findViewById(R.id.moveListRV)
        movesRV.layoutManager = LinearLayoutManager(activity)
        if(pokeMoves.isNotEmpty() && pokeMoveData.isNotEmpty()){
            movesRV.adapter = MovesAdapter(pokeMoves.sortedBy { it.learningLvl })
        }
        movesRV.adapter = MovesAdapter(pokeMoves.sortedBy { it.learningLvl })
        return view
    }

}