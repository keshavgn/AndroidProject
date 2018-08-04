package com.example.keshavanarasappa.androidproject.viewpager

import android.content.Context
import com.example.keshavanarasappa.androidproject.common.JsonHelper
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by keshava.narasappa on 08/04/18.
 */
object MovieHelper {

    const val KEY_TITLE = "title"
    const val KEY_RATING = "rating"
    const val KEY_POSTER_URI = "posterUri"
    const val KEY_OVERVIEW = "overview"

    fun getMoviesFromJson(fileName: String, context: Context): ArrayList<Movie> {
        val movies = ArrayList<Movie>()

        try {
            val jsonString = JsonHelper.loadJsonFromFile(fileName, context)
            val json = JSONObject(jsonString)
            val jsonMovies = json.getJSONArray("movies")

            for (index in 0 until jsonMovies.length()) {
                val movieTitle = jsonMovies.getJSONObject(index).getString(KEY_TITLE)
                val movieRating = jsonMovies.getJSONObject(index).getInt(KEY_RATING)
                val moviePosterUri = jsonMovies.getJSONObject(index).getString(KEY_POSTER_URI)
                val movieOverview = jsonMovies.getJSONObject(index).getString(KEY_OVERVIEW)
                movies.add(Movie(movieTitle, movieRating, moviePosterUri, movieOverview))
            }
        } catch (e: JSONException) {
            return movies
        }
        return movies
    }


}