package com.manshal_khatri.pikadex.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.manshal_khatri.pikadex.R
import com.manshal_khatri.pikadex.pokeTypeData
import com.manshal_khatri.pikadex.pokemon
import com.manshal_khatri.pikadex.util.TypeResourseSetter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TypeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TypeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var layoutAd2x : LinearLayout
    lateinit var layoutDisAd2x : LinearLayout
    lateinit var layoutAd4x : LinearLayout
    lateinit var layoutDisAd4x : LinearLayout
    lateinit var resourseSetter: TypeResourseSetter
//      var types : Types = Types("bug","ice")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_type, container, false)
        resourseSetter = TypeResourseSetter()
        layoutAd2x = view.findViewById(R.id.adItemContainer2x)
        layoutDisAd2x= view.findViewById(R.id.disItemContainer2x)
        layoutAd4x = view.findViewById(R.id.adItemContainer4x)
        layoutDisAd4x = view.findViewById(R.id.disItemContainer4x)
        val curPkmnType1 = pokemon?.pokeType?.type1
        val curPkmnType2 = pokemon?.pokeType?.type2
        if(curPkmnType2!=""){
            val advListType1 = pokeTypeData.find { curPkmnType1 == it.name }?.advantage
            val advListType2 = pokeTypeData.find { curPkmnType2 == it.name }?.advantage
            val disadvListType1 = pokeTypeData.find { curPkmnType1 == it.name }?.disAdvantage
            val disadvListType2 = pokeTypeData.find { curPkmnType2 == it.name }?.disAdvantage
            if (advListType1 != null  && advListType2 != null){
                val advIntersect = advListType1.intersect(advListType2)
                val adv2x = ((advListType1.union(advListType2)).minus(disadvListType1)).minus(disadvListType2).minus(advIntersect)
                setupTVs2(
                    adv2x as Set<String>,
                    layoutAd2x
                )
                setupTVs2(advIntersect ,layoutAd4x)
            }
            if (disadvListType2 != null && disadvListType1 != null) {
                val disadvIntersect = disadvListType1.intersect(disadvListType2)
                val disadv2x = (((disadvListType1.union(disadvListType2)).minus(advListType1)).minus(advListType2)).minus(disadvIntersect)
                setupTVs2(disadv2x as Set<String>,layoutDisAd2x)
                setupTVs2(disadvIntersect ,layoutDisAd4x)
            }
        }else{
            val kist = listOf<String>()
            val advListType1 = pokeTypeData.find { curPkmnType1 == it.name }?.advantage
            val disadvListType1 = pokeTypeData.find { curPkmnType1 == it.name }?.disAdvantage
            if (advListType1 != null ){
                setupTVs1(
                    advListType1,
                    layoutAd2x
                )
                setupTVs1(kist,layoutAd4x)
            }
            if (disadvListType1 != null ){
                setupTVs1(
                    disadvListType1,
                    layoutDisAd2x
                )
                setupTVs1(kist,layoutDisAd4x)
            }
        }

        return view
    }

    // WORKS WITH LIST
    fun setupTVs1(list: List<String>, parentLayout: LinearLayout){
            val x =LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,)
            if(list.isNotEmpty()){
                for(element in list.indices){
                    val newTV = TextView(activity)
                    with(newTV){
                        val type = list.elementAt(element)
                        text = type.replaceFirst(type.first(),type.first().uppercaseChar())
                        textSize = 18.0F
                        setTextColor(resources.getColor(resourseSetter.setTypeTextColor(type)))
                        setPadding(24,0,24,0)
                        x.setMargins(4,0,4,0)
                        layoutParams = x
                    }
                    newTV.setBackgroundResource(resourseSetter.setTypecolor(list.elementAt(element)))
                    parentLayout.addView(newTV)
                }
            }else{
                val newTV = TextView(activity)
                with(newTV){
                    text = "none"
                    textSize = 18.0F
                    setPadding(24,0,24,0)
                    x.setMargins(4,0,4,0)
                    layoutParams = x
                }
                parentLayout.addView(newTV)
            }

    }
    // WORKS WITH SET
    fun setupTVs2(list: Set<String>, parentLayout: LinearLayout){
        val x =LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,)
        if(list.isNotEmpty()) {
            for (element in list.indices) {
                val newTV = TextView(activity)
                with(newTV) {
                    val type = list.elementAt(element)
                    text = type.replaceFirst(type.first(),type.first().uppercaseChar())
                    textSize = 18.0F
                    setTextColor(resources.getColor(resourseSetter.setTypeTextColor(type)))
                    setPadding(24, 0, 24, 0)
                    x.setMargins(4, 0, 4, 0)
                    layoutParams = x
                }
                newTV.setBackgroundResource(resourseSetter.setTypecolor(list.elementAt(element)))
                parentLayout.addView(newTV)
            }
        } else{
                val newTV = TextView(activity)
                with(newTV){
                    text = "none"
                    textSize = 18.0F
                    setPadding(24,0,24,0)
                    x.setMargins(4,0,4,0)
                    layoutParams = x
                }
                parentLayout.addView(newTV)
            }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TypeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TypeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}