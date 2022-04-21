package com.manshal_khatri.pikadex.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.manshal_khatri.pikadex.R
import com.manshal_khatri.pikadex.databinding.MoveCardBinding
import com.manshal_khatri.pikadex.model.MoveData
import com.manshal_khatri.pikadex.model.Moves
import com.manshal_khatri.pikadex.pokeMoveData
import com.squareup.picasso.Picasso

class MovesAdapter(val moves : List<Moves> ) : RecyclerView.Adapter<MovesAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovesAdapter.ViewHolder {
        return ViewHolder(
            MoveCardBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: MovesAdapter.ViewHolder, position: Int) {
        val move = moves[position]
        val moveData = pokeMoveData.find { move.name == it.name }
        with(holder){
            lvl.text = move.learningLvl.toString()
            movename.text = move.name
           if(moveData!=null){
               movetype.text = moveData.type
               power.text = if(moveData.power==0){ "-"
               }else{ moveData.power.toString()
               }
               accuracy.text = if(moveData.accuracy==0) "-" else moveData.accuracy.toString()
               pp.text = moveData.pp.toString()
               /*when(moveData.kind){
                   "physical" -> Picasso.get().load(R.drawable.fire_wp).into(kind)
                   "special" -> Picasso.get().load(R.drawable.water_wp).into(kind)
                   "state" -> Picasso.get().load(R.drawable.flying_wp).into(kind)
                   else -> {}
               }*/
               if(moveData.kind == "physical"){
                   Picasso.get().load(R.drawable.fire_wp).into(kind)
               }else if(moveData.kind == "special"){
                   Picasso.get().load(R.drawable.water_wp).into(kind)
               }else{
                   Picasso.get().load(R.drawable.flying_wp).into(kind)
               }
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