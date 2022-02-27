package com.manshal_khatri.pikadex

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manshal_khatri.pikadex.databinding.ActivityMainBinding
import com.manshal_khatri.pikadex.model.Pokemons

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val pokemonsList = mutableListOf<Pokemons>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.toolbar)
        val pokemon = Pokemons(1,"pikachu","electric","water")
        pokemonsList.add(pokemon)
        pokemonsList.add(pokemon)

        binding.list.adapter = MyCardRecyclerViewAdapter(pokemonsList,this)



//        binding.navigator.setOnClickListener {
//            val intent = Intent(this@MainActivity,DescriptionActivity::class.java)
//            startActivity(intent)
//        }

//        if (recyclerView is RecyclerView) {
//            with(recyclerView) {
//                layoutManager = LinearLayoutManager(context)
//                adapter = RecyclerViewPostAdapter(fetchedPostList.filter { it.category == args.postCategory } as MutableList<PostDetails>,1)
//            }
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }



        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.





//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }
}