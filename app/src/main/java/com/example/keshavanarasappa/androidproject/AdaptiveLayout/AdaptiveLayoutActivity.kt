package com.example.keshavanarasappa.androidproject.AdaptiveLayout

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.keshavanarasappa.androidproject.R
import com.google.android.flexbox.FlexboxLayout
import kotlinx.android.synthetic.main.activity_adaptive.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset
import java.util.*

/**
 * Created by keshava.narasappa on 17/03/18.
 */
class AdaptiveLayoutActivity: AppCompatActivity() {

    private val locations = ArrayList<Location>()
    private lateinit var locationAdapter: LocationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adaptive)
        setupRecyclerView()
        if (savedInstanceState != null) {
            val index = savedInstanceState.getInt(SELECTED_LOCATION_INDEX)
            if (index >= 0 && index < locations.size) {
                locationAdapter.selectedLocationIndex = index
                loadForecast(locations[index].forecast)
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

        loadData()

        locationAdapter = LocationAdapter(this, locations, object : LocationAdapter.OnItemClickListener {
            override fun onItemClick(location: Location) {
                loadForecast(location.forecast)
            }
        })
        recyclerView.adapter = locationAdapter
    }

    private fun loadData() {
        val json: String? = loadJsonString()
        val array: JSONArray? = loadJsonArray(json)
        loadLocations(array)
    }

    private fun loadJsonString(): String? {
        var json: String? = null
        try {
            val inputStream = assets.open("data.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charset.forName("UTF-8"))
        } catch (e: IOException) {
            Log.e("MainActivity", e.toString())
        }
        return json
    }

    private fun loadJsonArray(json: String?): JSONArray? {
        var array: JSONArray? = null
        try {
            if (json != null) {
                array = JSONArray(json)
            }
        } catch (e: JSONException) {
            Log.e("MainActivity", e.toString())
        }
        return array
    }

    private fun loadLocations(array: JSONArray?) {
        if (array != null) {
            for (i in 0 until array.length()) {
                try {
                    val jsonObject = array[i] as JSONObject
                    val stringArray = jsonObject["forecast"] as JSONArray
                    val forecast = (0 until stringArray.length()).mapTo(ArrayList<String>()) { stringArray.getString(it) }
                    val location = Location(jsonObject["name"] as String, forecast)
                    locations.add(location)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
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