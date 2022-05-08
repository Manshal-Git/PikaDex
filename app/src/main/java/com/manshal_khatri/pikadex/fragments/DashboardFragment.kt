package com.manshal_khatri.pikadex.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manshal_khatri.pikadex.adapters.MyCardRecyclerViewAdapter
import com.manshal_khatri.pikadex.R
import com.manshal_khatri.pikadex.pokeMoveData
import com.manshal_khatri.pikadex.pokeTypeData
import com.manshal_khatri.pikadex.pokemonsList
import com.manshal_khatri.pikadex.viewmodel.PokemonViewmodel

class DashboardFragment : Fragment() {
    lateinit var list : RecyclerView
    lateinit var c : TextView
    var dataloaded = false
    private val _pokelistSize = MutableLiveData<Int>(pokemonsList.size)
    val pokelistSize : LiveData<Int>
    get() = _pokelistSize
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        c = view.findViewById(R.id.counter)
        c.text = pokemonsList.size.toString() + " " + pokeMoveData.size.toString() + " " + pokeTypeData.size.toString()
        list = view.findViewById(R.id.list)
        val vm = ViewModelProvider(this).get(PokemonViewmodel::class.java)
        vm.vmPokemonList.observe(viewLifecycleOwner, Observer {
            c.text = it.size.toString() + " " + pokeMoveData.size.toString() + " " + pokeTypeData.size.toString()
        })
       /* pokelistSize.observe(viewLifecycleOwner, Observer {
            c.text = pokemonsList.size.toString() + " " + pokeMoveData.size.toString() + " " + pokeTypeData.size.toString()
        })*/
        list.layoutManager = LinearLayoutManager(context)
        list.adapter= MyCardRecyclerViewAdapter(pokemonsList.sortedBy { it.id },view)
            return view
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}