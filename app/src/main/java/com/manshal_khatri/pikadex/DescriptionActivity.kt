package com.manshal_khatri.pikadex

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintSet
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.manshal_khatri.pikadex.databinding.ActivityDescriptionBinding
import com.manshal_khatri.pikadex.fragments.InfoFragment
import com.manshal_khatri.pikadex.fragments.LocationFragment
import com.manshal_khatri.pikadex.fragments.MovesFragment
import com.manshal_khatri.pikadex.model.MoveData
import com.manshal_khatri.pikadex.model.Moves
import com.manshal_khatri.pikadex.model.Pokemons
import com.squareup.picasso.Picasso
import kotlin.math.abs
var pokemon : Pokemons? = Pokemons() // CURRENT PKMN OBJ CAN BE USED IN CHILD FRAGMENTS
class DescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDescriptionBinding
 private  lateinit var mBinding: ConstraintSet
    private  lateinit var mBinding2: ConstraintSet
 var pokeId = 4

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mBinding = binding.root.getConstraintSet(R.id.end )
        mBinding2 = binding.root.getConstraintSet(R.id.start)

        val t1 = mBinding.getConstraint(R.id.type1)
        val t2 = mBinding2.getConstraint(R.id.type1)

         pokeId = intent.getIntExtra("id" , 1)
        val queue = Volley.newRequestQueue(this)
        getMoves(queue,pokeId)


        if (intent!=null){
            pokemon = pokemonsList.find {  pokeId == it.id  }
            val tmpPk = pokemon!!
            if (pokemon != null) {
                binding.pokeName.text = tmpPk.pokeName.replaceFirst(tmpPk.pokeName.first(),tmpPk.pokeName.first().uppercaseChar())
                Picasso.get().load(tmpPk.spriteUrl).into(binding.PokeSprite)

                if(tmpPk.pokeType.type2!=""){
                    binding.type2.text = tmpPk.pokeType.type2
                    t2.propertySet.visibility = VISIBLE
                    binding.type1.text = tmpPk.pokeType.type1
                    setTypeTextcolor(tmpPk.pokeType.type2,binding.type2)
                    tmpPk.pokeType.type1.let { setTypeTextcolor(it,binding.type1)
                        setTypeBG(it,binding.imageView)}
                }else{
                    binding.type1.visibility= GONE
                    t1.propertySet.visibility = GONE
//                    t2.propertySet.visibility = GONE
                    binding.type2.text = tmpPk.pokeType.type1
                    tmpPk.pokeType.type1.let { setTypeTextcolor(it,binding.type2)
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
                    supportFragmentManager.beginTransaction().replace(R.id.desc_frag_container,InfoFragment()).commitNow()
                    return@setOnNavigationItemSelectedListener  true
                }
                else -> {
                    supportFragmentManager.beginTransaction().replace(R.id.desc_frag_container,LocationFragment()).commitNow()
                    return@setOnNavigationItemSelectedListener  true
                }
            }
        }

        supportFragmentManager.beginTransaction().add(R.id.desc_frag_container,InfoFragment()).commit()

    }

     fun getMoves(queue : RequestQueue, pokeId : Int){
        val request = object : JsonObjectRequest(Method.GET, pokeApi+"$pokeId",null,Response.Listener {
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
    @SuppressLint("ResourceAsColor")
    fun setTypeTextcolor(type:String, holder : TextView){
        when(type){
            "grass" -> holder.setBackgroundResource(R.drawable.type_bg_grass)
            "poison" -> holder.setBackgroundResource(R.drawable.type_bg_poison)
            "fire" -> holder.setBackgroundResource(R.drawable.type_bg_fire)
            "water" -> holder.setBackgroundResource(R.drawable.type_bg_water)
            "electric" -> {holder.setBackgroundResource(R.drawable.type_bg_electric)
                holder.setTextColor(R.color.black)}
            "psychic" -> holder.setBackgroundResource(R.drawable.type_bg_psychic)
            "flying"-> {holder.setBackgroundResource(R.drawable.type_bg_flying)
                holder.setTextColor(R.color.black)}
            "ghost"-> holder.setBackgroundResource(R.drawable.type_bg_ghost)
            "normal"-> {holder.setBackgroundResource(R.drawable.type_bg_normal)
                holder.setTextColor(R.color.black)}
            "fairy" -> holder.setBackgroundResource(R.drawable.type_bg_fairy)
            "fighting" -> holder.setBackgroundResource(R.drawable.type_bg_fighting)
            "ground" -> holder.setBackgroundResource(R.drawable.type_bg_ground)
            "rock" -> holder.setBackgroundResource(R.drawable.type_bg_rock)
            "bug" -> holder.setBackgroundResource(R.drawable.type_bg_bug)
            "steel" -> holder.setBackgroundResource(R.drawable.type_bg_steel)
            "ice" -> holder.setBackgroundResource(R.drawable.type_bg_ice)
            "dragon" -> holder.setBackgroundResource(R.drawable.type_bg_dragon)
            "dark" -> holder.setBackgroundResource(R.drawable.type_bg_dark)
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
            "fairy" -> holder.setBackgroundResource(R.drawable.fairy_wp)
            "fighting" -> holder.setBackgroundResource(R.drawable.fighting_wp)
            "ground" -> holder.setBackgroundResource(R.drawable.ground_wp)
            "rock" -> holder.setBackgroundResource(R.drawable.rock_wp)
            "bug" -> holder.setBackgroundResource(R.drawable.bug_wp)
            "steel" -> holder.setBackgroundResource(R.drawable.steel_wp)
            "ice" -> holder.setBackgroundResource(R.drawable.ice_wp)
            "dragon" -> holder.setBackgroundResource(R.drawable.dragon_wp)
            "dark" -> holder.setBackgroundResource(R.drawable.dark_wp)
        }
    }
    /*fun setStatusbar(){
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor=this.resources.getColor(R.color.TintSky)
    }*/
}
