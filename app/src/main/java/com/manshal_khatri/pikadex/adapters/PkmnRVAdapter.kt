package com.manshal_khatri.pikadex.adapters


import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.manshal_khatri.pikadex.DescriptionActivity
import com.manshal_khatri.pikadex.databinding.FragmentCardBinding
import com.manshal_khatri.pikadex.model.Pokemons
import com.manshal_khatri.pikadex.util.TypeResourseSetter


class PkmnRVAdapter(
     val values: List<Pokemons> ,view : View
) : RecyclerView.Adapter<PkmnRVAdapter.ViewHolder>() {
    val v = view
    private val viewFormatter = TypeResourseSetter()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.pokecard.setOnClickListener {
            val intent = Intent(it.context, DescriptionActivity::class.java)
            intent.putExtra("id",item.id)
            it.context.startActivity(intent)
        }
        holder.pokeName.text =  viewFormatter.capitalize(item.pokeName)
        holder.pokeType1.text = viewFormatter.capitalize(item.pokeType.type1)
        holder.pokeType1.setTextColor(v.resources.getColor(viewFormatter.setTypeTextColor(item.pokeType.type1)))
        holder.pokeType1.setBackgroundResource(viewFormatter.setTypecolor(item.pokeType.type1))
        if(item.pokeType.type2!=""){
            holder.pokeType2.text = viewFormatter.capitalize(item.pokeType.type2)
            holder.pokeType2.setTextColor(v.resources.getColor(viewFormatter.setTypeTextColor(item.pokeType.type2)))
            holder.pokeType2.setBackgroundResource(viewFormatter.setTypecolor(item.pokeType.type2))
            holder.pokeType2.visibility = VISIBLE
        }else{
            holder.pokeType2.visibility = GONE
        }
        Glide.with(v).load(item.spriteUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.pokeSprite)
    }


    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val pokecard : LinearLayout = binding.pokeCard
        val pokeName  = binding.pokeName
        val pokeType1 = binding.poketype1
        val pokeType2 = binding.poketype2
        val pokeSprite = binding.PokeSprite

    }

}