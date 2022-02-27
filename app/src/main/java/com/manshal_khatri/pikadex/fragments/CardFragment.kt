package com.manshal_khatri.pikadex.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.manshal_khatri.pikadex.MyCardRecyclerViewAdapter
import com.manshal_khatri.pikadex.R
import com.manshal_khatri.pikadex.fragments.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class CardFragment : Fragment() {

    private var columnCount = 3

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        arguments?.let {
//            columnCount = it.getInt(ARG_COLUMN_COUNT)
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_card, container, false)
////
//        // Set the adapter
//        if (view is RecyclerView) {
//            with(view) {
//                layoutManager = when {
//                    columnCount <= 1 -> LinearLayoutManager(context)
//                    else -> GridLayoutManager(context, columnCount)
//                }
//                adapter = MyCardRecyclerViewAdapter(PlaceholderContent.ITEMS)
//            }
//        }
//        return view
//    }
//
//    companion object {
//
//        // TODO: Customize parameter argument names
//        const val ARG_COLUMN_COUNT = "column-count"
//
//        // TODO: Customize parameter initialization
//        @JvmStatic
//        fun newInstance(columnCount: Int) =
//            CardFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(ARG_COLUMN_COUNT, columnCount)
//                }
//            }
//    }
}