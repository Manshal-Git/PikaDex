package com.manshal_khatri.pikadex


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.manshal_khatri.pikadex.databinding.FragmentCardBinding
import com.manshal_khatri.pikadex.fragments.placeholder.PlaceholderContent.PlaceholderItem
import com.manshal_khatri.pikadex.model.Pokemons
import com.squareup.picasso.Picasso


class MyCardRecyclerViewAdapter(
     val values: List<Pokemons> ,view : View
) : RecyclerView.Adapter<MyCardRecyclerViewAdapter.ViewHolder>() {
    val v = view
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
            val intent = Intent(it.context,DescriptionActivity::class.java)
            intent.putExtra("id",item.id)
            it.context.startActivity(intent)
        }
        holder.pokeName.text =  item.pokeName
        holder.pokeType1.text = item.pokeType.type1
        setTypecolor(item.pokeType.type1,holder.pokeType1)
        if(item.pokeType.type2!=""){
            holder.pokeType2.text = item.pokeType.type2
            setTypecolor(item.pokeType.type2,holder.pokeType2)
            holder.pokeType2.visibility = VISIBLE
        }else{
            holder.pokeType2.visibility = GONE
        }

        Glide.with(v).load(item.spriteUrl).into(holder.pokeSprite)
       // Picasso.get().load(item.spriteUrl).into(holder.pokeSprite)
    }


    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val pokecard : LinearLayout = binding.pokeCard
        val pokeName  = binding.pokeName
        val pokeType1 = binding.poketype1
        val pokeType2 = binding.poketype2
        val pokeSprite = binding.PokeSprite

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
            "ghost"-> holder.setBackgroundResource(R.drawable.type_bg_ghost)

            "flying"-> {holder.setBackgroundResource(R.drawable.type_bg_flying)
                holder.setTextColor(R.color.black)}
            "normal"-> { holder.setBackgroundResource(R.drawable.type_bg_normal)
                        holder.setTextColor(R.color.black)}
        }
    }

}