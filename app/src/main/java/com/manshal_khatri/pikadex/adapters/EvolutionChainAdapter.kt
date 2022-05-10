package com.manshal_khatri.pikadex.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.manshal_khatri.pikadex.DescriptionActivity
import com.manshal_khatri.pikadex.databinding.ChainElementBinding
import com.manshal_khatri.pikadex.pokemonsList
import com.squareup.picasso.Picasso

class EvolutionChainAdapter (val list: List<String> , val activity: DescriptionActivity) : RecyclerView.Adapter<EvolutionChainAdapter.ViewHolder>(){
    class ViewHolder(binding: ChainElementBinding):RecyclerView.ViewHolder(binding.root){
            val sprite = binding.IVPokeSprite
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ChainElementBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pkmn = list[position]
        val pokemon = pokemonsList.find { pkmn == it.pokeName }
        if (pokemon != null) {
            Picasso.get().load(pokemon.spriteUrl).into(holder.sprite)
            holder.sprite.setOnClickListener {
//                activity.restart(pokemon.id)
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}