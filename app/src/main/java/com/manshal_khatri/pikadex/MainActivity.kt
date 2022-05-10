package com.manshal_khatri.pikadex

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.GestureDetector
import android.view.Menu
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.manshal_khatri.pikadex.databinding.ActivityMainBinding
import com.manshal_khatri.pikadex.fragments.DashboardFragment
import com.manshal_khatri.pikadex.model.*
import com.manshal_khatri.pikadex.room.RoomDB
import com.manshal_khatri.pikadex.util.APIs
import com.manshal_khatri.pikadex.viewmodel.PokemonViewmodel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

val pokeApi = APIs.PKMN_API
val pokemonsList = mutableListOf<Pokemons>()                                 // PKMNS
val pokeMoves = mutableListOf<Moves>()                                      // PKMN MOVES
val pokeMoveData = mutableListOf<MoveData>()                              // MOVE DEATILS
val pokeTypeData = mutableListOf<TypesData>()                              //PKMN TYPES DATA
var start = 1

class MainActivity : AppCompatActivity() {

    var x1 : Float = 0.0f
    var x2 : Float = 0.0f
    var y1 : Float = 0.0f
    var y2 : Float = 0.0f
    lateinit var gd : GestureDetector
    companion object{
        const val TH = 200
    }
   // private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    var dataSaved = false
    var pokedataSaved = false
    var typedataSaved = false
    var movedataSaved = false
    lateinit var sharedPreferences: SharedPreferences
    lateinit var pokeDB : RoomDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("pokeDB", MODE_PRIVATE)
        Glide.with(this).load(R.drawable.pikantro).into(binding.imageView2)
        val queue = Volley.newRequestQueue(this)
        val typeQueue = Volley.newRequestQueue(this)
        val movesQueue = Volley.newRequestQueue(this)
         pokeDB = RoomDB.getDatabase(this)
        val vm = ViewModelProvider(this).get(PokemonViewmodel::class.java)
//        pokemonsList.add(Pokemons())                  // DEPRECATED
//        pokeMoveData.add(MoveData(1,"scratch",100,100,25,"normal","special"))     //DEPRECATED
        pokedataSaved = sharedPreferences.getBoolean("have_pokedata",pokedataSaved)
        typedataSaved = sharedPreferences.getBoolean("have_typedata",typedataSaved)
        movedataSaved = sharedPreferences.getBoolean("have_movedata",movedataSaved)
        vm.clearAllPkmn()
        pokeMoveData.clear()  // Not efficient Should be using viewmodel
        pokeTypeData.clear()
//        gd = GestureDetector(this,this)

        GlobalScope.launch {
            if (pokeDB.pkmnDao().isUpToDate() < APIs.LAST_POKEMON - 1) {
                for (i in start until APIs.LAST_POKEMON) {
                    val reqPkms = object :
                        JsonObjectRequest(
                            Request.Method.GET,
                            APIs.PKMN_API + "$i",
                            null,
                            Response.Listener {

                                println("Category API success ${it.getInt("id")}")
                                val pokeObj = it
                                val statsArr = pokeObj.getJSONArray("stats")
                                var si = 0
                                if (pokeObj.getJSONArray("types")
                                        .length() == 2
                                ) {   // IF POKEMON HAS 2 TYPES

                                       val pkmn = Pokemons(
                                            pokeObj.getInt("id"),
                                            pokeObj.getString("name"),
                                            PokeTypes(
                                                pokeObj.getJSONArray("types").getJSONObject(0)
                                                    .getJSONObject("type")
                                                    .getString("name"),
                                                pokeObj.getJSONArray("types").getJSONObject(1)
                                                    .getJSONObject("type")
                                                    .getString("name")
                                            ),
                                            pokeObj.getJSONObject("sprites").getJSONObject("other")
                                                .getJSONObject("home").getString("front_default"),
                                            Stats(
                                                statsArr.getJSONObject(si++).getInt("base_stat")
                                                    .toString(),
                                                statsArr.getJSONObject(si++).getInt("base_stat")
                                                    .toString(),
                                                statsArr.getJSONObject(si++).getInt("base_stat")
                                                    .toString(),
                                                statsArr.getJSONObject(si++).getInt("base_stat")
                                                    .toString(),
                                                statsArr.getJSONObject(si++).getInt("base_stat")
                                                    .toString(),
                                                statsArr.getJSONObject(si).getInt("base_stat")
                                                    .toString(),
                                            )
                                        )
                                    GlobalScope.launch { pokeDB.pkmnDao().storePokemon(pkmn) }
//                                    pokemonsList.add(pkmn)
                                    vm.addPkmn(pkmn)
                                } else {


                                    val pkmn = Pokemons(
                                        pokeObj.getInt("id"),
                                        pokeObj.getString("name"),
                                        PokeTypes(
                                            pokeObj.getJSONArray("types").getJSONObject(0)
                                                .getJSONObject("type")
                                                .getString("name"), ""
                                        ),
                                        pokeObj.getJSONObject("sprites").getJSONObject("other")
                                            .getJSONObject("home").getString("front_default"),
                                        Stats(
                                            statsArr.getJSONObject(si++).getInt("base_stat")
                                                .toString(),
                                            statsArr.getJSONObject(si++).getInt("base_stat")
                                                .toString(),
                                            statsArr.getJSONObject(si++).getInt("base_stat")
                                                .toString(),
                                            statsArr.getJSONObject(si++).getInt("base_stat")
                                                .toString(),
                                            statsArr.getJSONObject(si++).getInt("base_stat")
                                                .toString(),
                                            statsArr.getJSONObject(si).getInt("base_stat")
                                                .toString()
                                        )
                                    )
                                    GlobalScope.launch { pokeDB.pkmnDao().storePokemon(pkmn) }
//                                    pokemonsList.add(pkmn)
                                    vm.addPkmn(pkmn)
                                }

                            },
                            Response.ErrorListener {

                            }) {

                    }
                    queue.add(reqPkms)
                }
                /*dataSaved = true
                sharedPreferences.edit().putBoolean("have_data", dataSaved).apply()*/
            }else{
                pokedataSaved = true
                sharedPreferences.edit().putBoolean("have_pokedata", pokedataSaved).apply()
                supportFragmentManager.beginTransaction().replace(R.id.pokeList_container, DashboardFragment()).commit()

            }
            if (pokeDB.typeDao().isUpToDate() < APIs.LAST_TYPE - 1) {
                for (i in 1 until APIs.LAST_TYPE) {
                    val reqTypes = object :
                        JsonObjectRequest(Request.Method.GET,
                            APIs.TYPES_API + "$i",
                            null,
                            Response.Listener {
                                println(" $i type fetching succeed")
                                val ad = it.getJSONObject("damage_relations")
                                    .getJSONArray("double_damage_from")
                                val adL = mutableListOf<String>()
                                val dd = it.getJSONObject("damage_relations")
                                    .getJSONArray("half_damage_from")
                                val ddL = mutableListOf<String>()
                                val nd =
                                    it.getJSONObject("damage_relations")
                                        .getJSONArray("no_damage_from")
                                val ndL = mutableListOf<String>()
                                var x = 0
                                var y = 0
                                var z = 0
                                while (x < ad.length() || y < dd.length() || z < nd.length()) {
                                    if (x < ad.length()) {
                                        ddL.add(ad.getJSONObject(x).getString("name"))
                                        x++
                                    }
                                    if (y < dd.length()) {
                                        adL.add(dd.getJSONObject(y).getString("name"))
                                        y++
                                    }
                                    if (z < nd.length()) {
                                        ndL.add(nd.getJSONObject(z).getString("name"))
                                        z++
                                    }
                                }
                                val type = TypesData(
                                    it.getInt("id"),
                                    it.getString("name"), adL, ddL, ndL
                                )
                                GlobalScope.launch {
                                    pokeDB.typeDao().storeType(type)
                                }
                                pokeTypeData.add(type)
                            },
                            Response.ErrorListener {
                                println("type fetching error")
                            }) {}
                    typeQueue.add(reqTypes)
                }
            }else{
                typedataSaved = true
                sharedPreferences.edit().putBoolean("have_typedata", typedataSaved).apply()
            }
            if ( pokeDB.moveDao().isUpToDate() < APIs.MOVE_LIMIT - 1) {
                for (i in 1 until APIs.MOVE_LIMIT) {
                    val reqTypes = object :
                        JsonObjectRequest(Request.Method.GET,
                            APIs.Moves_API + "$i",
                            null,
                            Response.Listener { jsonObject ->
                                println(" $i move fetching succeed")
                                val kind =
                                    jsonObject.getJSONObject("damage_class").getString("name")
                                var power: Int
                                var acc: Int
                                try {
                                    power = jsonObject.getInt("power")
                                } catch (e: Exception) {
                                    power = 0
                                }
                                try {
                                    acc = jsonObject.getInt("accuracy")
                                } catch (e: Exception) {
                                    acc = 0
                                }
                                try {

                                    val move = MoveData(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("name"),
                                        power,
                                        acc,
                                        jsonObject.getInt("pp"),
                                        jsonObject.getJSONObject("type").getString("name"), kind
                                    )
                                    GlobalScope.launch {
                                        pokeDB.moveDao().storeMove(move)
                                    }
                                    pokeMoveData.add(move)
                                } catch (e: Exception) {
                                    println("OCUURED : $e")
                                }
                            },
                            Response.ErrorListener {
                                println("move fetching error")
                            }) {}
                    movesQueue.add(reqTypes)
                }
            }else{
                movedataSaved = true
                sharedPreferences.edit().putBoolean("have_movedata", movedataSaved).apply()
            }
        }
        if(typedataSaved) {
            pokeDB.typeDao().getAllTypes().observe(this, androidx.lifecycle.Observer {
                pokeTypeData.addAll(it)
            })
        }
        if(pokedataSaved) {
            // GETTING DATA FROM LOCAL DATABASE(ROOM)
            pokeDB.pkmnDao().getAllPokemon().observe(this, androidx.lifecycle.Observer {
//                pokemonsList.addAll(it)
                vm.addallPkmn(it)
            })
        }
        if(movedataSaved) {
            pokeDB.moveDao().getAllMoves().observe(this, androidx.lifecycle.Observer {
                pokeMoveData.addAll(it)
            })
        }

        Handler().postDelayed({

            binding.imageView2.visibility = GONE
            binding.pokeListContainer.background = null
//            pokemonsList.removeFirst()            // DEPRECATED
            supportFragmentManager.beginTransaction().replace(R.id.pokeList_container, DashboardFragment()).commit()
        },3500)

        binding.button.setOnClickListener {
           supportFragmentManager.beginTransaction().replace(R.id.pokeList_container, DashboardFragment()).commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

 /*   override fun onDown(e: MotionEvent?): Boolean {
        return false
    }

    override fun onShowPress(e: MotionEvent?) {


    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {

        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {

        return false
    }

    override fun onLongPress(e: MotionEvent?) {

    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gd.onTouchEvent(event)
        when(event?.action){
            0 ->{
                x1 = event.x
                y1=event.y
            }
            1-> {
                x2 = event.x
                y2=event.y
                val v1 : Float = x2-x1
                val v2 : Float = y2-y1
                if(abs(v1) > TH){
                    if(x2 > x1){
                        println("RIGHT")
                        Toast.makeText(this, "Right", Toast.LENGTH_SHORT).show()
                    }else{
                        println("LEFT")
                        Toast.makeText(this, "Left", Toast.LENGTH_SHORT).show()
                    }
                }else if(abs(v2) > TH){
                    if(y2 > y1){
                        println("BOTTOM")
                        Toast.makeText(this, "Bottom", Toast.LENGTH_SHORT).show()
                    }else{
                        println("TOP")
                        Toast.makeText(this, "Top", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }*/

}