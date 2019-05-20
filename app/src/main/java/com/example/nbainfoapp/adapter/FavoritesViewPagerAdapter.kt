package com.example.nbainfoapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.nbainfoapp.R
import com.example.nbainfoapp.fragment.FilmsFavoritesFragment
import com.example.nbainfoapp.fragment.PeopleFavoritesFragment
import com.example.nbainfoapp.fragment.PlanetsFavoritesFragment

private const val NUMBER_OF_TABS = 3

class FavoritesViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> PeopleFavoritesFragment()
        1 -> PlanetsFavoritesFragment()
        else -> FilmsFavoritesFragment()
    }

    override fun getCount(): Int = NUMBER_OF_TABS

    override fun getPageTitle(position: Int): CharSequence? = when (position) {
        0 -> "People"
        1 -> "Planets"
        else -> "Films"
    }
}