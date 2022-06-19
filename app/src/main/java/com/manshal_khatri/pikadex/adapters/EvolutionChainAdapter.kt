package com.manshal_khatri.pikadex.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.manshal_khatri.pikadex.DescriptionActivity
import com.manshal_khatri.pikadex.databinding.ChainElementBinding
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
        println("curPokemon is $pkmn")
//        pkmn = pkmn.toString().substringBefore("-")

        val pokemon = pokemonsList.find { pkmn == it.pokeName }
        if (pokemon != null) {
//            Picasso.get().load(pokemon.spriteUrl)into(holder.sprite)
    Glide.with(activity).load(pokemon.spriteUrl)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(holder.sprite)
            holder.sprite.setOnClickListener {
                activity.startActivity(Intent(activity,DescriptionActivity::class.java).putExtra("id",pokemon.id))
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