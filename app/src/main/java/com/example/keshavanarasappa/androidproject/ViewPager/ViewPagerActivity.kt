package com.example.keshavanarasappa.androidproject.viewpager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.keshavanarasappa.androidproject.R
import com.example.keshavanarasappa.androidproject.viewpager.MovieHelper
import com.example.keshavanarasappa.androidproject.viewpager.MoviesPagerAdapter
import kotlinx.android.synthetic.main.activity_view_pager.*

class ViewPagerActivity : AppCompatActivity() {
    private lateinit var pagerAdapter: MoviesPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)
        val movies = MovieHelper.getMoviesFromJson("movies.json", this)
        pagerAdapter = MoviesPagerAdapter(supportFragmentManager, movies)
        viewPager.adapter = pagerAdapter
        viewPager.currentItem = pagerAdapter.count / 2
        recyclerTabLayout.setUpWithViewPager(viewPager)
    }
}
