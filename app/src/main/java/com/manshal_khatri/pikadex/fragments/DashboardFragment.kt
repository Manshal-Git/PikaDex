package com.manshal_khatri.pikadex.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.manshal_khatri.pikadex.adapters.PkmnRVAdapter
import com.manshal_khatri.pikadex.R
import com.manshal_khatri.pikadex.pokeMoveData
import com.manshal_khatri.pikadex.pokeTypeData
import com.manshal_khatri.pikadex.pokemonsList
import com.manshal_khatri.pikadex.viewmodel.PokemonViewmodel

class DashboardFragment : Fragment() {
    lateinit var list : RecyclerView
    lateinit var c : TextView
    lateinit var searchBar : androidx.appcompat.widget.SearchView
    lateinit var btnSearch : FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        Handler().postDelayed({
            val layout = view.findViewById<FrameLayout>(R.id.frameLayoutDashboard)
            layout.visibility = VISIBLE
        },1000)
        c = view.findViewById(R.id.counter)
        list = view.findViewById(R.id.list)
        btnSearch = view.findViewById(R.id.btnSearch)
        searchBar = view.findViewById(R.id.searchBar)
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(activity, query, Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!=""){
                    list.adapter= PkmnRVAdapter(pokemonsList.filter { it.pokeName.contains(newText!!) },view)
                }else{
                    list.adapter= PkmnRVAdapter(pokemonsList.sortedBy { it.id },view)
                }
                return true
            }
        })
        searchBar.setOnCloseListener(object : SearchView.OnCloseListener,
            androidx.appcompat.widget.SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                searchBar.visibility = GONE
                return true
            }
        })
        btnSearch.setOnClickListener{
            searchBar.visibility = VISIBLE
            c.visibility = GONE
            searchBar.isIconified = false
            searchBar.focusSearch(View.FOCUS_LEFT)
        }


        val vm = ViewModelProvider(this).get(PokemonViewmodel::class.java)
        vm.vmPokemonList.observe(viewLifecycleOwner, Observer {
            c.text = it.size.toString() + " " + pokeMoveData.size.toString() + " " + pokeTypeData.size.toString()
        })
        list.layoutManager = LinearLayoutManager(context)
        list.adapter= PkmnRVAdapter(pokemonsList.sortedBy { it.id },view)
            return view
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}