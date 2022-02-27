package com.manshal_khatri.pikadex


import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.manshal_khatri.pikadex.databinding.FragmentCardBinding
import com.manshal_khatri.pikadex.fragments.placeholder.PlaceholderContent.PlaceholderItem
import com.manshal_khatri.pikadex.model.Pokemons

//import com.manshal_khatri.pikadex.fragments.databinding.FragmentCardBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyCardRecyclerViewAdapter(
     val values: MutableList<Pokemons> , context : Context
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
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val pokecard : LinearLayout = binding.pokeCard
    }

}