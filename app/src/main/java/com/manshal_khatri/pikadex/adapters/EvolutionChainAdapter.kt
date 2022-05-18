package com.manshal_khatri.pikadex.adapters

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import android.webkit.RenderProcessGoneDetail
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.manshal_khatri.pikadex.DescriptionActivity
import com.manshal_khatri.pikadex.databinding.ChainElementBinding
import com.manshal_khatri.pikadex.pokemonsList
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class EvolutionChainAdapter (val list: List<String>) : RecyclerView.Adapter<EvolutionChainAdapter.ViewHolder>(){
    val avoidThese = listOf(0,1,2,3)
//    val mlist = list.dropLast(1)
    class ViewHolder(binding: ChainElementBinding):RecyclerView.ViewHolder(binding.root){
            val sprite = binding.IVPokeSprite
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ChainElementBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var pkmn = list[position]
        println("curPokemon is $pkmn")
        pkmn = pkmn.toString().substringBefore("-")
        val pokemon = pokemonsList.find { pkmn == it.pokeName }
        if (pokemon != null) {
            Picasso.get().load(pokemon.spriteUrl).into(holder.sprite)
            holder.sprite.setOnClickListener {
//                activity.restart(pokemon.id)
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

}