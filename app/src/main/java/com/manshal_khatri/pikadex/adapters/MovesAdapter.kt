package com.manshal_khatri.pikadex.adapters

import android.graphics.drawable.TransitionDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.manshal_khatri.pikadex.R
import com.manshal_khatri.pikadex.databinding.MoveCardBinding
import com.manshal_khatri.pikadex.model.MoveData
import com.manshal_khatri.pikadex.model.Moves
import com.manshal_khatri.pikadex.pokeMoveData
import com.manshal_khatri.pikadex.util.TypeResourseSetter
import com.squareup.picasso.Picasso

class MovesAdapter(private val moves : List<Moves> ,val activity : FragmentActivity ) : RecyclerView.Adapter<MovesAdapter.ViewHolder>()  {
    val colorSetter = TypeResourseSetter()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MoveCardBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val move = moves[position]
        val moveData = pokeMoveData.find { move.name == it.name }
        println(moveData)
        with(holder){
            lvl.text = move.learningLvl.toString()
            movename.text = colorSetter.capitalize(move.name)
           if(moveData!=null){
               with(moveData.type){
                   movetype.text = colorSetter.capitalize(this)
                   movetype.setTextColor(activity.resources.getColor(colorSetter.setTypeTextColor(this)))
                   movetype.setBackgroundResource(colorSetter.setTypecolor(this))
               }
               power.text = if(moveData.power==0){ "-"
               }else{ moveData.power.toString()
               }
               accuracy.text = if(moveData.accuracy==0) "-" else moveData.accuracy.toString()
               pp.text = moveData.pp.toString()
               when(moveData.kind){
                   "physical" ->Glide.with(activity).load(R.drawable.ic_physical_attack).into(kind)
                   "special" -> Glide.with(activity).load(R.drawable.ic_special_attack).into(kind)
                   "status" -> Glide.with(activity).load(R.drawable.ic_stat_attack).into(kind)
               }
               kind.tooltipText=moveData.kind
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