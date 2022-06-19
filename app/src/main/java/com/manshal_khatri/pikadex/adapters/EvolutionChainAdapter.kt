package com.manshal_khatri.pikadex.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.manshal_khatri.pikadex.DescriptionActivity
import com.manshal_khatri.pikadex.databinding.ChainElementBinding
import com.manshal_khatri.pikadex.model.Pokemons
import com.manshal_khatri.pikadex.pokemon
import com.manshal_khatri.pikadex.pokemonsList

class EvolutionChainAdapter (val list: List<String>,val activity: FragmentActivity) : RecyclerView.Adapter<EvolutionChainAdapter.ViewHolder>(){
    class ViewHolder(binding: ChainElementBinding):RecyclerView.ViewHolder(binding.root){
            val sprite = binding.IVPokeSprite
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ChainElementBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var pkmn = list[position]
        val pkmn2 = pkmn.toString().substringBefore("-")
//        println("$pkmn $pkmn2")
        val pokemon = pokemonsList.find { pkmn == it.pokeName }
        val pokemonFormed =  pokemonsList.find { pkmn2 == it.pokeName.substringBefore("-") }
//        println("$pokemon $pokemonFormed")
        with(holder) {
            if (pokemon != null) {
                with(pokemon) {
                    loadImage(spriteUrl, sprite)
                    setOnClickNavigateListener(sprite,id)
                }
            }else if(pokemonFormed!=null){
                with(pokemonFormed) {
                    loadImage(spriteUrl, sprite)
                    setOnClickNavigateListener(sprite,id)
                }
            }
        }
        /*if(!avoidThese.contains(pkmn)) {

        }else{
            holder.itemView.visibility = GONE
        }*/
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun loadImage(spriteUrl : String, imageView : ImageView){
        Glide.with(activity).load(spriteUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }
    fun setOnClickNavigateListener(view : View,id : Int){
        view.setOnClickListener {
            activity.startActivity(Intent(activity,DescriptionActivity::class.java).putExtra("id",id))
        }
    }

}