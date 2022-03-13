package com.manshal_khatri.pikadex

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.manshal_khatri.pikadex.model.Pokemons
import com.manshal_khatri.pikadex.model.Types

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var list : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val queue = Volley.newRequestQueue(activity)

        for(i in start until limit){

            val reqPkms = object : JsonObjectRequest(Request.Method.GET, pokeApi+"$i",null, {

                println("Category API success $it")
                val pokeObj = it
                if(pokeObj.getJSONArray("types").length() == 2) {

                    pokemonsList.add(
                        Pokemons(
                            pokeObj.getInt("id"),
                            pokeObj.getString("name")
                            , Types(
                                pokeObj.getJSONArray("types").getJSONObject(0)
                                    .getJSONObject("type")
                                    .getString("name"),
                                pokeObj.getJSONArray("types").getJSONObject(1)
                                    .getJSONObject("type")
                                    .getString("name")
                            ),pokeObj.getJSONObject("sprites").getJSONObject("other").getJSONObject("home").getString("front_default")
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
                                    .getString("name"),
                            ),pokeObj.getJSONObject("sprites").getJSONObject("other").getJSONObject("home").getString("front_default")
                        )
                    )
                }
//                    start++                       //THIS HAVE TO BE CHECKED
            }, Response.ErrorListener {
                Toast.makeText(activity ,"Something got wrong" , Toast.LENGTH_SHORT).show()
            }){

            }
            queue.add(reqPkms)


//            binding.list.adapter?.notifyDataSetChanged()

        }
        list = view.findViewById(R.id.list)
        with(list) {
            adapter = MyCardRecyclerViewAdapter(pokemonsList, view)
            visibility = View.VISIBLE
            start+=limit
            limit+=limit

        }

            return view
        }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}