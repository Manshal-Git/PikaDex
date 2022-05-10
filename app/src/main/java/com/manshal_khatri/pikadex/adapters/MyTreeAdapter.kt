package com.manshal_khatri.pikadex.adapters

import android.content.Context
import android.database.DataSetObserver
import android.view.View
import com.manshal_khatri.pikadex.databinding.ChainElementBinding
import com.manshal_khatri.pikadex.pokemonsList
import com.squareup.picasso.Picasso
import de.blox.treeview.BaseTreeAdapter
import de.blox.treeview.TreeNode

class MyTreeAdapter(context: Context, layoutRes: Int) : BaseTreeAdapter<TreeViewHolder>(context,
    layoutRes
){
 override fun onCreateViewHolder(view: View?): TreeViewHolder {
        return TreeViewHolder(view)
    }
    override fun onBindViewHolder(viewHolder: TreeViewHolder?, data: Any?, position: Int) {
        if (viewHolder != null) {
            val pkmn = data.toString()
            val spriteUrl = pokemonsList.find { pkmn == it.pokeName }?.spriteUrl
            Picasso.get().load(spriteUrl).into(viewHolder.sprite)
        }
    }


}
