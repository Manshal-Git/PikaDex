package com.manshal_khatri.pikadex.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.manshal_khatri.pikadex.R
import com.manshal_khatri.pikadex.chainDataSaved
import com.manshal_khatri.pikadex.model.EvolutionChainObj
import com.manshal_khatri.pikadex.room.RoomDB
import com.manshal_khatri.pikadex.util.APIs
import com.manshal_khatri.pikadex.viewmodel.PokemonViewmodel
import kotlinx.coroutines.*
import org.json.JSONArray

class GenericInfoFragment : Fragment() {
    private var evoChainLvl = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_info,container,false)
        return view
    }

    fun getEvoChain(queue: RequestQueue,pokeId: Int,vm : PokemonViewmodel,db: RoomDB){
        CoroutineScope(Dispatchers.Main).launch{
            getEvolutionChain(queue,pokeId,vm,db)
        }
    }
    private suspend fun getSpecies(queue: RequestQueue, pokeId: Int): String? {
        var s : String? = null
        val job = CoroutineScope(Dispatchers.IO).launch{
            val request = object : JsonObjectRequest(
                Method.GET,
                APIs.SPECIES_API + "$pokeId",
                null,
                Response.Listener {
                    println("Specie Response success $it")
                    s = (it.getJSONObject("evolution_chain").getString("url"))
                },
                Response.ErrorListener {
                    println("Specie Response Got Error $it")
                }) {

            }
            queue.add(request)
        }
        delay(2000)
        return s
    }

   private suspend fun getEvolutionChain(queue: RequestQueue, pokeId: Int,vm: PokemonViewmodel,db: RoomDB){
//         evolutionChain.value?.clear()
//        vm.clearEvolutionChain()
        CoroutineScope(Dispatchers.IO).launch {
            val chainUrl = CoroutineScope(Dispatchers.IO).async {
                getSpecies(queue, pokeId)
            }
            val url = chainUrl.await()
            println(url)
            val request = object : JsonObjectRequest(
                Method.GET,
                url,
                null,
                Response.Listener {
//                    println("EvolutionChain Response success $it")
                    val curObj = it.getJSONObject("chain")
                    vm.addToEvolutionChain(evoChainLvl.toString())
                    vm.addToEvolutionChain(curObj.getJSONObject("species").getString("name"))
                    fetchChain(curObj.getJSONArray("evolves_to"),vm)
                    GlobalScope.launch {
                        if (url != null) {
                            db.evoChainDao().storeChain(
                                EvolutionChainObj(url.substringAfter("https://pokeapi.co/api/v2/evolution-chain/")+"$pokeId",
                                    vm.vmEvolutionChain.value as List<String>))
                        }
                    }
                    println(vm.vmEvolutionChain.value.toString())
//                    chainDataSaved = true
                },
                Response.ErrorListener {
                    println("EvolutionChain Response Got Error $it")
                }) {

            }
            queue.add(request)
        }
    }
   private fun fetchChain(next : JSONArray,vm : PokemonViewmodel){
        if(next.length()==0){
            return
        }
      evoChainLvl++
       vm.addToEvolutionChain(evoChainLvl.toString())
        for(i in 0 until next.length()){
            val curObj = next.getJSONObject(i)
            vm.addToEvolutionChain(curObj.getJSONObject("species").getString("name"))
            fetchChain(curObj.getJSONArray("evolves_to"),vm)
        }
    }
    }

