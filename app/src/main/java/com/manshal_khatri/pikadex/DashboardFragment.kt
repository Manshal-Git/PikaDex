package com.manshal_khatri.pikadex

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.manshal_khatri.pikadex.model.Pokemons
import com.manshal_khatri.pikadex.model.Types

class DashboardFragment : Fragment() {
    lateinit var list : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        list = view.findViewById(R.id.list)

        val queue = Volley.newRequestQueue(activity as Context)

        for(i in start until limit){

            val reqPkms = object : JsonObjectRequest(Request.Method.GET, pokeApi+"$i",null,Response.Listener {

                println("Category API success ${it.getInt("id")}")
                val pokeObj = it
                if(pokeObj.getJSONArray("types").length() == 2) {   // IF POKEMON HAS 2 TYPES

                    pokemonsList.add(
                        Pokemons(
                            pokeObj.getInt("id")
                            , pokeObj.getString("name")+pokeObj.getInt("id").toString()
                            , Types(
                                pokeObj.getJSONArray("types").getJSONObject(0)
                                    .getJSONObject("type")
                                    .getString("name"),
                                pokeObj.getJSONArray("types").getJSONObject(1)
                                    .getJSONObject("type")
                                    .getString("name")
                            )
                            ,pokeObj.getJSONObject("sprites").getJSONObject("other").getJSONObject("home").getString("front_default")
                        )
                    )
                }else{
                    pokemonsList.add(
                        Pokemons(
                            pokeObj.getInt("id"),
                            pokeObj.getString("name")
                            , Types(
                                pokeObj.getJSONArray("types").getJSONObject(0)
                                    .getJSONObject("type")
                                    .getString("name"),null
                            ),pokeObj.getJSONObject("sprites").getJSONObject("other").getJSONObject("home").getString("front_default")
                        )
                    )
                }
//                    start++                       //THIS HAVE TO BE CHECKED
            }, Response.ErrorListener {
                Toast.makeText(activity as Context ,"Something got wrong" , Toast.LENGTH_SHORT).show()
            }){

            }
            queue.add(reqPkms)
        }

        list.layoutManager = LinearLayoutManager(context)
        list.adapter=MyCardRecyclerViewAdapter(pokemonsList.sortedBy { it.id },view)

            return view
        }

}