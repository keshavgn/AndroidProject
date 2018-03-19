package com.example.keshavanarasappa.androidproject.AdaptiveLayout

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.keshavanarasappa.androidproject.R
import com.google.android.flexbox.FlexboxLayout
import kotlinx.android.synthetic.main.activity_adaptive.*

/**
 * Created by keshava.narasappa on 17/03/18.
 */
class AdaptiveLayoutActivity: AppCompatActivity() {

    private lateinit var locationAdapter: LocationAdapter
    internal lateinit var viewModel: AdaptiveViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adaptive)
        viewModel = AdaptiveViewModel.create(this)
        setupRecyclerView()
        if (savedInstanceState != null) {
            val index = savedInstanceState.getInt(SELECTED_LOCATION_INDEX)
            if (index >= 0 && index < viewModel.numberOfLocations()) {
                locationAdapter.selectedLocationIndex = index
                loadForecast(viewModel.forcastForLocaiton(index))
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_LOCATION_INDEX, locationAdapter.selectedLocationIndex)
    }

    private fun setupRecyclerView() {
        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.layoutManager = layoutManager
        val inputStream = assets.open("data.json")
        viewModel.loadData(inputStream)

        locationAdapter = LocationAdapter(this, viewModel.locations(), object : LocationAdapter.OnItemClickListener {
            override fun onItemClick(location: Location) {
                loadForecast(location.forecast)
            }
        })
        recyclerView.adapter = locationAdapter
    }

    private fun mapWeatherToDrawable(forecast: String): Drawable? {
        var drawableId = 0
        when (forecast) {
            "sun" -> drawableId = R.drawable.ic_sun
            "rain" -> drawableId = R.drawable.ic_rain
            "fog" -> drawableId = R.drawable.ic_fog
            "thunder" -> drawableId = R.drawable.ic_thunder
            "cloud" -> drawableId = R.drawable.ic_cloud
            "snow" -> drawableId = R.drawable.ic_snow
        }
        return ContextCompat.getDrawable(this, drawableId)
    }

    private fun loadForecast(forecast: List<String>) {
        val forecastView = findViewById<View>(R.id.forecast) as FlexboxLayout
        for (i in 0 until forecastView.childCount) {
            val dayView = forecastView.getChildAt(i) as AppCompatImageView
            dayView.setImageDrawable(mapWeatherToDrawable(forecast[i]))
        }
    }

    companion object {
        private const val SELECTED_LOCATION_INDEX = "selectedLocationIndex"
    }
}