package com.manshal_khatri.pikadex.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.manshal_khatri.pikadex.R
import com.manshal_khatri.pikadex.databinding.FragmentStatsBinding
import com.manshal_khatri.pikadex.pokemon

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StatsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StatsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentStatsBinding
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
        val view= inflater.inflate(R.layout.fragment_stats, container, false)
        binding = FragmentStatsBinding.bind(view)
        binding.PB1.progress = pokemon?.stats?.hp!!
        binding.PB2.progress = pokemon?.stats?.attack!!
        binding.PB3.progress = pokemon?.stats?.defence!!
        binding.PB4.progress = pokemon?.stats?.spAtk!!
        binding.PB5.progress = pokemon?.stats?.spDef!!
        binding.PB6.progress = pokemon?.stats?.speed!!
        binding.TV1.text = pokemon?.stats?.hp.toString()
        binding.TV2.text = pokemon?.stats?.attack.toString()
        binding.TV3.text = pokemon?.stats?.defence.toString()
        binding.TV4.text = pokemon?.stats?.spAtk.toString()
        binding.TV5.text = pokemon?.stats?.spDef.toString()
        binding.TV6.text = pokemon?.stats?.speed.toString()
        binding.TVAvg.text = binding.TVAvg.text.toString() + ((pokemon?.stats?.hp!!+pokemon?.stats?.attack!!+pokemon?.stats?.defence!!+pokemon?.stats?.spAtk!!+pokemon?.stats?.spDef!!+pokemon?.stats?.speed!!)/6).toString()// + "/255"
      return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StatsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StatsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}