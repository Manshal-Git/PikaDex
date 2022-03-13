package com.manshal_khatri.pikadex


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.manshal_khatri.pikadex.databinding.FragmentCardBinding
import com.manshal_khatri.pikadex.fragments.placeholder.PlaceholderContent.PlaceholderItem
import com.manshal_khatri.pikadex.model.Pokemons
import com.squareup.picasso.Picasso

//import com.manshal_khatri.pikadex.fragments.databinding.FragmentCardBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyCardRecyclerViewAdapter(
     val values: List<Pokemons> , view : View
) : RecyclerView.Adapter<MyCardRecyclerViewAdapter.ViewHolder>() {

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
//            Navigation.findNavController(it).navigate(R.id.firstFragment)
            val intent = Intent(it.context,DescriptionActivity::class.java)
            intent.putExtra("id",item.id)
            it.context.startActivity(intent)
        }
        holder.pokeName.text =  item.pokeName
        holder.pokeType1.text = item.pokeType.type1
        item.pokeType.type1?.let { setTypecolor(it,holder.pokeType1) }
//        println(item.pokeType.type1 + " " + item.pokeType.type2)
        if(item.pokeType.type2!=null){
            holder.pokeType2.text = item.pokeType.type2
            setTypecolor(item.pokeType.type2,holder.pokeType2)
            holder.pokeType2.visibility = VISIBLE
        }
//      Picasso.get().load(item.spriteUrl).into(holder.pokeSprite)
        Picasso.get().load(item.spriteUrl).into(holder.pokeSprite)
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
            "flying"-> {holder.setBackgroundResource(R.drawable.type_bg_flying)
                        holder.setTextColor(R.color.black)}
            "ghost"-> holder.setBackgroundResource(R.drawable.type_bg_ghost)
            "normal"-> holder.setBackgroundResource(R.drawable.type_bg_normal)
        }
    }


}