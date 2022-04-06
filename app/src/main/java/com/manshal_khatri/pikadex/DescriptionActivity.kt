package com.manshal_khatri.pikadex

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View.GONE
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.manshal_khatri.pikadex.databinding.ActivityDescriptionBinding
import com.manshal_khatri.pikadex.fragments.StatsFragment
import com.manshal_khatri.pikadex.fragments.LocationFragment
import com.manshal_khatri.pikadex.fragments.MovesFragment
import com.manshal_khatri.pikadex.model.MoveData
import com.manshal_khatri.pikadex.model.Moves
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDescriptionBinding


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pokeId = intent.getIntExtra("id" , 1)
        val queue = Volley.newRequestQueue(this)

       GlobalScope.launch {  getMoves(queue,pokeId) }
        pokeMoveData = getMovedata(queue)




        if (intent!=null){
            val pokemon = pokemonsList.find {  pokeId == it.id  }
            if (pokemon != null) {
                binding.pokeName.text = pokemon.pokeName
                Picasso.get().load(pokemon.spriteUrl).into(binding.PokeSprite)

                if(pokemon.pokeType.type2!=""){
                    binding.type2.text = pokemon.pokeType.type2
                    binding.type1.text = pokemon.pokeType.type1
                    setTypecolor(pokemon.pokeType.type2,binding.type2)
                    pokemon.pokeType.type1.let { setTypecolor(it,binding.type1)
                        setTypeBG(it,binding.imageView)}

                }else{
                    binding.type1.visibility= GONE
                    binding.type2.text = pokemon.pokeType.type1
                    pokemon.pokeType.type1.let { setTypecolor(it,binding.type2)
                        setTypeBG(it,binding.imageView)}
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
                    supportFragmentManager.beginTransaction().replace(R.id.desc_frag_container,StatsFragment()).commitNow()
                    return@setOnNavigationItemSelectedListener  true
                }
                else -> {
                    supportFragmentManager.beginTransaction().replace(R.id.desc_frag_container,LocationFragment()).commitNow()
                    return@setOnNavigationItemSelectedListener  true
                }
            }
        }

        supportFragmentManager.beginTransaction().add(R.id.desc_frag_container,StatsFragment()).commit()

    }

    suspend fun getMoves(queue : RequestQueue,pokeId : Int){
        val request = object : JsonObjectRequest(Method.GET, pokeApi+"$pokeId",null,Response.Listener {
            print("Api Response success $it")
             pokeMoves.clear()
            // Getting moves
            val moveslist = it.getJSONArray("moves")
            for( i in 0 until moveslist.length()){
                val move = moveslist.getJSONObject(i)
                //println(move.getJSONObject("move").getString("name"))
                val lateVer = move.getJSONArray("version_group_details")
                if(lateVer.getJSONObject(lateVer.length()-1).getJSONObject("move_learn_method").getString("name")=="level-up"){
                    pokeMoves.add( Moves(i,
                        lateVer.getJSONObject(lateVer.length()-1).getInt("level_learned_at"),
                        move.getJSONObject("move").getString("name"),
                        move.getJSONObject("move").getString("url")
                    )
                    )
                }
            }
        },Response.ErrorListener {
            print("Api Response failed $it")
        }){

        }
        queue.add(request)
        //getMovedata(queue)
    }
     fun getMovedata(queue: RequestQueue) : MutableList<MoveData>{
        val movedata = mutableListOf<MoveData>()

        if(pokeMoves.isNotEmpty()){
            //pokeMoveData.clear()
            for(element in pokeMoves){
                println(element)
                val requesting = object : JsonObjectRequest(Method.GET, element.url,null,Response.Listener {
                    print("Api Response success $it")
                    // Getting movesdata
                    movedata.add(
                        MoveData(
                            element.mid,
                            it.getInt("power"),
                            it.getInt("accuracy"),
                            it.getInt("pp"),"norma",
                            "physical"
                        ))
                },Response.ErrorListener {
                    println("$it OCCURED HERE")
                }){}
                queue.add(requesting)
            }
        }
        return movedata
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    /*suspend fun getMovedata(string: String) : MoveData {
        var mymove : MoveData = MoveData(110,100,10,"normal","kind")
        val queuem = Volley.newRequestQueue(this)
        val req = object : JsonObjectRequest(Method.GET, string,null,Response.Listener{
            print("success with move")
            mymove = MoveData(
                it.getInt("power"),
                it.getInt("accuracy"),
                it.getInt("pp"),"norma",
                "physical"
            )
        }, Response.ErrorListener {
            print("error with move")
        }) {

        }
        queuem.add(req)

        return mymove
    }*/
    @SuppressLint("ResourceAsColor")
    fun setTypecolor(type:String , holder : TextView){
        when(type){
            "grass" -> holder.setBackgroundResource(R.drawable.type_bg_grass)
            "poison" -> holder.setBackgroundResource(R.drawable.type_bg_poison)
            "fire" -> holder.setBackgroundResource(R.drawable.type_bg_fire)
            "water" -> holder.setBackgroundResource(R.drawable.type_bg_water)
            "electric" -> holder.setBackgroundResource(R.drawable.type_bg_electric)
            "psychic" -> holder.setBackgroundResource(R.drawable.type_bg_psychic)
            "flying"-> {holder.setBackgroundResource(R.drawable.type_bg_flying)
                holder.setTextColor(R.color.black)}
            "ghost"-> holder.setBackgroundResource(R.drawable.type_bg_ghost)
            "normal"-> holder.setBackgroundResource(R.drawable.type_bg_normal)
        }
    }
    fun setTypeBG(type: String, holder: ImageView){
        when(type){
            "grass" ->  holder.setImageResource(R.drawable.grass_wp)
            "poison" -> holder.setImageResource(R.drawable.posion_wp)
            "fire" -> holder.setImageResource(R.drawable.fire_wp)
            "water" -> holder.setImageResource(R.drawable.water_wp)
            "electric" -> holder.setImageResource(R.drawable.electric_wp)
            "psychic" -> holder.setImageResource(R.drawable.psychic_wp)
            "flying"-> holder.setImageResource(R.drawable.flying_wp)
            "ghost"-> holder.setImageResource(R.drawable.ghost_wp)
            "normal"-> holder.setImageResource(R.drawable.normal_wp)
        }

    }

    /*fun setStatusbar(){
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor=this.resources.getColor(R.color.TintSky)
    }*/
}