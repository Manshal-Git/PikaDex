package com.manshal_khatri.pikadex.fragments

import android.content.Context
import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manshal_khatri.pikadex.DescriptionActivity
import com.manshal_khatri.pikadex.R
import com.manshal_khatri.pikadex.adapters.EvolutionChainAdapter
import com.manshal_khatri.pikadex.adapters.MyTreeAdapter
import com.manshal_khatri.pikadex.adapters.TreeViewHolder
import com.manshal_khatri.pikadex.databinding.FragmentEvolutionChainBinding
import com.manshal_khatri.pikadex.evolutionChain
import com.manshal_khatri.pikadex.pokemonsList
import com.manshal_khatri.pikadex.viewmodel.PokemonViewmodel
import com.squareup.picasso.Picasso
import de.blox.treeview.BaseTreeAdapter
import de.blox.treeview.TreeNode
import kotlinx.coroutines.*
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EvolutionChainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EvolutionChainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var mainLL : LinearLayoutCompat
    lateinit var binding: FragmentEvolutionChainBinding
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
       val view = inflater.inflate(R.layout.fragment_evolution_chain, container, false)
        binding = FragmentEvolutionChainBinding.bind(view)
//        val vm = ViewModelProvider(this).get(PokemonViewmodel::class.java)
        binding.RVEvolutionChain.layoutManager = LinearLayoutManager(activity,RecyclerView.HORIZONTAL,false)
//        val reqList = removeNumbers()

        Handler().postDelayed({
            setAdapter()
        },3000)
        Handler().postDelayed({
          setAdapter()
        },4000) // SHOULD BE ENHANCED
        return view
    }
    fun setAdapter(){
        try{
//                vm.removeNumbers()
            binding.RVEvolutionChain.adapter = activity?.let {
                EvolutionChainAdapter(
                    evolutionChain, it
                )
            }
        }catch (e : Exception){
            Log.d("Tag","Exception Catched : ",e)
        }
    }
    fun seePokemon(pokeId : Int){
        startActivity(Intent(activity,DescriptionActivity::class.java).putExtra("id",pokeId))
    }
    /*fun removeNumbers():List<String>{
        for(i in 0 until 3)
            evolutionChain.remove(i.toString())

        println("numbers removes $evolutionChain")
        return evolutionChain
    }*/

    override fun onResume() {
        setAdapter()
        super.onResume()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EvolutionChainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EvolutionChainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}