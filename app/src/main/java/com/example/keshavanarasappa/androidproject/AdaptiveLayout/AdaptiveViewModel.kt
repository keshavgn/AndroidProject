package com.example.keshavanarasappa.androidproject.AdaptiveLayout

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.util.Log
import com.example.keshavanarasappa.androidproject.Common.JsonHelper
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import java.util.ArrayList

/**
 * Created by keshava.narasappa on 18/03/18.
 */
class AdaptiveViewModel: ViewModel() {
    private val locations = ArrayList<Location>()

    fun loadData(filename: String, context: Context) {
        val json: String? = JsonHelper.loadJsonFromFile(filename, context)
        val array: JSONArray? = loadJsonArray(json)
        loadLocations(array)
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

    fun numberOfLocations(): Int {
        return locations.size
    }

    fun locations(): ArrayList<Location> {
        return locations
    }

    fun forcastForLocation(index: Int): List<String> {
        return locations[index].forecast
    }

    companion object {

        fun create(activity: AdaptiveLayoutActivity): AdaptiveViewModel {
            return ViewModelProviders.of(activity).get(AdaptiveViewModel::class.java)
        }

    }
}