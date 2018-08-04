package com.example.keshavanarasappa.androidproject.viewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.keshavanarasappa.androidproject.viewpager.MovieFragment

/**
 * Created by keshava.narasappa on 08/04/18.
 */

class MoviesPagerAdapter(fragmentManager: FragmentManager, private val movies: ArrayList<Movie>) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return MovieFragment.newInstance(movies[position % movies.size])
    }

    override fun getCount(): Int {
        return movies.size * MAX_VALUE
    }

    companion object {
        private const val MAX_VALUE = 200
    }

    override fun getPageTitle(position: Int): CharSequence {
        return movies[position % movies.size].title
    }
}