package com.manshal_khatri.pikadex

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BlendMode
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.motion.widget.MotionLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.manshal_khatri.pikadex.databinding.ActivityDescriptionBinding
import com.manshal_khatri.pikadex.databinding.ActivityMainBinding
import com.manshal_khatri.pikadex.fragments.FirstFragment
import com.manshal_khatri.pikadex.fragments.LocationFragment
import com.manshal_khatri.pikadex.fragments.MovesFragment
import com.manshal_khatri.pikadex.model.Moves
import com.manshal_khatri.pikadex.model.Pokemons
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDescriptionBinding
    val pokeMoves = mutableListOf<Moves>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

//        val actionBar = actionBar
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val toolbar  = binding.collaspingToolbar
//        val appbar  = binding.appBar
//        appbar.verticalFadingEdgeLength
        val pokeId = intent.getIntExtra("id" , 1)
        val queue = Volley.newRequestQueue(this)
        val queuem = Volley.newRequestQueue(this)
        GlobalScope.launch {


        }
        val request = object : JsonObjectRequest(Method.GET, pokeApi+"$pokeId",null,Response.Listener {
            print("Api Response success $it")
                // Getting moves
            val moveslist = it.getJSONArray("moves")
            for( i in 0 until moveslist.length()){
                val move = moveslist.getJSONObject(i)
               println(move.getJSONObject("move").getString("name"))
                val lateVer = move.getJSONArray("version_group_details")
                if(lateVer.getJSONObject(lateVer.length()-1).getJSONObject("move_learn_method").getString("name")=="level-up"){
                    Moves(
                        lateVer.getJSONObject(lateVer.length()-1).getInt("level_learned_at"),
                        move.getJSONObject("move").getString("name"),

                    )
                }
            }

        },Response.ErrorListener {

        }){

        }
        queue.add(request)


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
                        setTypeBG(it,binding.relativeLayout2)}

                }else{
                    binding.type1.visibility= GONE
                    binding.type2.text = pokemon.pokeType.type1
                    pokemon.pokeType.type1.let { setTypecolor(it,binding.type2)
                        setTypeBG(it,binding.relativeLayout2)}
                }



            }
        }

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_moves -> {
                    supportFragmentManager.beginTransaction().replace(R.id.desc_frag_container,MovesFragment()).commit()
                    return@setOnNavigationItemSelectedListener  true
                }
                R.id.action_stats -> {
                    supportFragmentManager.beginTransaction().replace(R.id.desc_frag_container,FirstFragment()).commit()
                    return@setOnNavigationItemSelectedListener  true
                }
                else -> {
                    supportFragmentManager.beginTransaction().replace(R.id.desc_frag_container,LocationFragment()).commit()
                    return@setOnNavigationItemSelectedListener  true
                }
            }
        }
        supportFragmentManager.beginTransaction().add(R.id.desc_frag_container,FirstFragment()).commit()


    }
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
    @RequiresApi(Build.VERSION_CODES.M)
    fun setTypeBG(type: String, holder: MotionLayout){
        when(type){
            "grass" -> holder.setBackgroundResource(R.drawable.grass_wp)
            "poison" -> holder.setBackgroundResource(R.drawable.posion_wp)
            "fire" -> holder.setBackgroundResource(R.drawable.fire_wp)
            "water" -> holder.setBackgroundResource(R.drawable.water_wp)
            "electric" -> holder.setBackgroundResource(R.drawable.electric_wp)
            "psychic" -> holder.setBackgroundResource(R.drawable.psychic_wp)
            "flying"-> holder.setBackgroundResource(R.drawable.flying_wp)
            "ghost"-> holder.setBackgroundResource(R.drawable.ghost_wp)
            "normal"-> holder.setBackgroundResource(R.drawable.normal_wp)
        }

    }
    /*fun setStatusbar(){
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor=this.resources.getColor(R.color.TintSky)
    }*/
}