package com.manshal_khatri.pikadex.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.manshal_khatri.pikadex.R
import com.manshal_khatri.pikadex.databinding.MoveCardBinding
import com.manshal_khatri.pikadex.model.Moves
import com.squareup.picasso.Picasso

class MovesAdapter(val moves : List<Moves> , view : View) : RecyclerView.Adapter<MovesAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovesAdapter.ViewHolder {
        return ViewHolder(
            MoveCardBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: MovesAdapter.ViewHolder, position: Int) {
        val move = moves[position]
        with(holder){
            lvl.text = move.learningLvl.toString()
            movename.text = move.name
            movetype.text = move.type
            power.text = move.power.toString()
            accuracy.text = move.accuracy.toString()
            pp.text = move.pp.toString()
            when(move.kind){
                "Physical" -> Picasso.get().load(R.drawable.fire_wp).into(kind)
                "Special" -> Picasso.get().load(R.drawable.water_wp).into(kind)
                "State" -> Picasso.get().load(R.drawable.flying_wp).into(kind)
                else -> {}
            }

        }


    }

    override fun getItemCount(): Int {
        return moves.size
    }
    class ViewHolder(binding : MoveCardBinding) : RecyclerView.ViewHolder(binding.root){

        val lvl = binding.lvl
        val movename = binding.moveName
        val movetype = binding.type
        val power = binding.movePower
        val accuracy = binding.moveAccuracy
        val pp = binding.movePP
        val kind = binding.moveKind
    }
}