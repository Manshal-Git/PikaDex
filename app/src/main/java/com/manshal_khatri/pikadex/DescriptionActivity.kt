package com.manshal_khatri.pikadex

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModelProvider
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.manshal_khatri.pikadex.adapters.MyTreeAdapter
import com.manshal_khatri.pikadex.adapters.TreeViewHolder
import com.manshal_khatri.pikadex.databinding.ActivityDescriptionBinding
import com.manshal_khatri.pikadex.fragments.GenericInfoFragment
import com.manshal_khatri.pikadex.fragments.LocationFragment
import com.manshal_khatri.pikadex.fragments.MovesFragment
import com.manshal_khatri.pikadex.model.Moves
import com.manshal_khatri.pikadex.model.Pokemons
import com.manshal_khatri.pikadex.util.APIs
import com.manshal_khatri.pikadex.util.TypeResourseSetter
import com.manshal_khatri.pikadex.viewmodel.PokemonViewmodel
import com.squareup.picasso.Picasso
import de.blox.treeview.BaseTreeAdapter
import kotlinx.coroutines.*
import org.json.JSONArray

var pokemon : Pokemons? = Pokemons() // CURRENT PKMN OBJ CAN BE USED IN CHILD FRAGMENTS
val evolutionChain  = mutableListOf<String>()
class DescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDescriptionBinding
 private  lateinit var mBinding: ConstraintSet
    private  lateinit var mBinding2: ConstraintSet
 var pokeId = 4
    lateinit var vm : PokemonViewmodel

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mBinding = binding.root.getConstraintSet(R.id.end )
        mBinding2 = binding.root.getConstraintSet(R.id.start)
        vm = ViewModelProvider(this).get(PokemonViewmodel::class.java)
        val t1 = mBinding.getConstraint(R.id.type1)
        val t2 = mBinding2.getConstraint(R.id.type1)
        val colorSetter = TypeResourseSetter()


         pokeId = intent.getIntExtra("id" , 1)
        val queue = Volley.newRequestQueue(this)
        getMoves(queue,pokeId)
       CoroutineScope(Dispatchers.Main).launch{
           getEvolutionChain(queue,pokeId)
       }


        if (intent!=null){
            pokemon = pokemonsList.find {  pokeId == it.id  }
            val tmpPk = pokemon!!
            if (pokemon != null) {
                binding.pokeName.text = tmpPk.pokeName.replaceFirst(tmpPk.pokeName.first(),tmpPk.pokeName.first().uppercaseChar())
                Picasso.get().load(tmpPk.spriteUrl).into(binding.PokeSprite)
                binding.textView.text = tmpPk.id.toString()
                if(tmpPk.pokeType.type2!=""){
                    binding.type2.text = tmpPk.pokeType.type2
                    t2.propertySet.visibility = VISIBLE
                    binding.type1.text = tmpPk.pokeType.type1
                    binding.type2.setBackgroundResource(colorSetter.setTypecolor(tmpPk.pokeType.type2))
                    tmpPk.pokeType.type1.let { binding.type1.setBackgroundResource(colorSetter.setTypecolor(it))
                        binding.imageView.setImageResource(colorSetter.setTypeBG(it))}
                }else{
                    binding.type1.visibility= GONE
                    t1.propertySet.visibility = GONE
//                    t2.propertySet.visibility = GONE
                    binding.type2.text = tmpPk.pokeType.type1
                    tmpPk.pokeType.type1.let { binding.type2.setBackgroundResource(colorSetter.setTypecolor(it))
                        binding.imageView.setImageResource(colorSetter.setTypeBG(it))}
                }
            }
        }
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_moves -> {
                    supportFragmentManager.beginTransaction().replace(R.id.desc_frag_container,MovesFragment()).commitNow()
                    return@setOnNavigationItemSelectedListener  true
                }
                R.id.action_stats -> {
                    supportFragmentManager.beginTransaction().replace(R.id.desc_frag_container,GenericInfoFragment()).commitNow()
                    return@setOnNavigationItemSelectedListener  true
                }
                else -> {
                    supportFragmentManager.beginTransaction().replace(R.id.desc_frag_container,LocationFragment()).commitNow()
                    return@setOnNavigationItemSelectedListener  true
                }
            }
        }

        supportFragmentManager.beginTransaction().add(R.id.desc_frag_container,GenericInfoFragment()).commit()

    }


    suspend fun getSpecies(queue: RequestQueue, pokeId: Int): String? {
        var s : String? = null
          val job = CoroutineScope(Dispatchers.IO).launch{
            val request = object : JsonObjectRequest(
                Method.GET,
                APIs.SPECIES_API + "$pokeId",
                null,
                Response.Listener {
                    println("Specie Response success $it")
                    s = (it.getJSONObject("evolution_chain").getString("url"))
                    /*obj = CoroutineScope(Dispatchers.IO).async{
                it
            }*/


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

     suspend fun getEvolutionChain(queue: RequestQueue,pokeId: Int){
//         evolutionChain.value?.clear()
         vm.clearEvolutionChain()
         CoroutineScope(Dispatchers.IO).launch {
             val chainUrl = CoroutineScope(Dispatchers.IO).async {
                 getSpecies(queue, pokeId)
             }
             val url = chainUrl.await()

             val request = object : JsonObjectRequest(
                 Method.GET,
                 url,
                 null,
                 Response.Listener {
                     println("EvolutionChain Response success $it")
                     var curObj = it.getJSONObject("chain")
                     vm.addToEvolutionChain(curObj.getJSONObject("species").getString("name"))
                     fetchChain(curObj.getJSONArray("evolves_to"))
                     /*do {
//                         evolutionChain.value?.add(curObj.getJSONObject("species").getString("name"))

                         var next = curObj.getJSONArray("evolves_to")
                         if (next.length() > 0) {
                             for(i in 0 until next.length()){
                                 curObj = next.getJSONObject(i)
                                 vm.addToEvolutionChain(curObj.getJSONObject("species").getString("name"))
                             }
                         } else {
                             break
                         }
                         println(vm.vmEvolutionChain.value.toString())
                     } while (true)*/
                     println(vm.vmEvolutionChain.value.toString())
                 },
                 Response.ErrorListener {
                     println("EvolutionChain Response Got Error $it")
                 }) {

             }
             queue.add(request)
         }

    }
    fun fetchChain(next : JSONArray){
        if(next.length()==0){
            return
        }
        for(i in 0 until next.length()){
            val curObj = next.getJSONObject(i)
            vm.addToEvolutionChain(curObj.getJSONObject("species").getString("name"))
            fetchChain(curObj.getJSONArray("evolves_to"))
        }
    }
    fun getMoves(queue : RequestQueue, pokeId : Int){
        val request = object : JsonObjectRequest(Method.GET, APIs.PKMN_API+"$pokeId",null,Response.Listener {
            print("Api Response success $it")
             pokeMoves.clear()
            // Getting moves
            val moveslist = it.getJSONArray("moves")
            for( i in 0 until moveslist.length()){
                val move = moveslist.getJSONObject(i)
                val latestVer = move.getJSONArray("version_group_details")
                if(latestVer.getJSONObject(latestVer.length()-1).getJSONObject("move_learn_method").getString("name")=="level-up"){
                    pokeMoves.add( Moves(
                        latestVer.getJSONObject(latestVer.length()-1).getInt("level_learned_at"),
                        move.getJSONObject("move").getString("name")
                    )
                    )
                }
            }
        },Response.ErrorListener {
            print("Api Response failed $it")
        }){}
        queue.add(request)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
   /* fun restart(id : Int){
        val intent = Intent(this,DescriptionActivity::class.java)
        intent.putExtra("id" , id)
        startActivity(intent)
    }*/
    /*fun setStatusbar(){
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor=this.resources.getColor(R.color.TintSky)
    }*/
}
