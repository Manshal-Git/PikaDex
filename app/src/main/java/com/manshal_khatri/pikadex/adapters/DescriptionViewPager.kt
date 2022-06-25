package com.manshal_khatri.pikadex.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.manshal_khatri.pikadex.fragments.GenericInfoFragment
import com.manshal_khatri.pikadex.fragments.LocationFragment
import com.manshal_khatri.pikadex.fragments.MovesFragment
import com.manshal_khatri.pikadex.fragments.StatsFragment

class DescriptionViewPager(context: Context,fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            1 -> MovesFragment()
            2 -> LocationFragment()
            else -> {GenericInfoFragment()}
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            1 -> "Moves"
            2 -> "Map"
            else -> "Stats"
        }
    }

}