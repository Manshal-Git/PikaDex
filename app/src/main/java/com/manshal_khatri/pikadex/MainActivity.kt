package com.manshal_khatri.pikadex

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.manshal_khatri.pikadex.databinding.ActivityMainBinding
import com.manshal_khatri.pikadex.model.Pokemons
import org.json.JSONObject

val pokeApi =  "https://pokeapi.co/api/v2/pokemon/"
val pokemonsList = mutableListOf<Pokemons>()
var start = 1
var limit = 10

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding



    lateinit var tmpjsonObject : JSONObject


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

     /*   val queue = Volley.newRequestQueue(this)

        for(i in 1 until limit){

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
             },Response.ErrorListener {
                 Toast.makeText(this,"Something got wrong" , Toast.LENGTH_SHORT).show()
             }){

             }
            queue.add(reqPkms)
            if(start==limit){

//                supportFragmentManager.beginTransaction().replace(R.id.pokeList_container, DashboardFragment()).commit()
            }

//            binding.list.adapter?.notifyDataSetChanged()

        }*/
        supportFragmentManager.beginTransaction().replace(R.id.pokeList_container, DashboardFragment()).commit()




    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

        fun callTypeAPI(api : String) : JSONObject{

        var jsonObject : JSONObject
        val queue = Volley.newRequestQueue(this)

        val reqPkms = object : JsonObjectRequest(Request.Method.GET,api,null, {

            println("Category API success $it")
            @Volatile
             tmpjsonObject = it
            /* val typesJsonArray = it.getJSONArray("types")
             val typeObjs = if (typesJsonArray.length()==2){
                 Types(
                     typesJsonArray.getJSONObject(0).getJSONObject("type").getString("name"),
                     typesJsonArray.getJSONObject(1).getJSONObject("type").getString("name")
                 )
             }else{
                 Types(typesJsonArray.getJSONObject(0).getJSONObject("type").getString("name"))
             }*/


          //  println(typesJsonArray.getJSONObject(0).getJSONObject("type").getString("name"),)


        },Response.ErrorListener {

        }){

        }
        queue.add(reqPkms)
           jsonObject = tmpjsonObject
        return jsonObject

    }
}