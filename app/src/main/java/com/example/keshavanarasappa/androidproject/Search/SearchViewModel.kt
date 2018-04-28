package com.example.keshavanarasappa.androidproject.Search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import org.json.JSONArray
import com.example.keshavanarasappa.androidproject.Search.Resource.Status.*
import okhttp3.OkHttpClient
import java.io.IOException

/**
 * Created by keshava.narasappa on 10/03/18.
 */
open class SearchViewModel: ViewModel() {

    private val searchResults = MutableLiveData<Resource<JSONArray>>()

    fun updateSearchResults(item: Resource<JSONArray>) {
        searchResults.postValue(item)
    }

    fun getSearchResults(): MutableLiveData<Resource<JSONArray>> {
        return searchResults
    }
    fun queryBooks(searchString: String) {

        var urlString = ""
        try {
            urlString = URLEncoder.encode(searchString, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        val client = OkHttpClient()
        val request = okhttp3.Request.Builder().url(QUERY_URL + urlString).build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                updateSearchResults(Resource.error(AppException(e)))
            }

            @Throws(IOException::class)
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                val result = response.body()!!.string()
                val jsonObject = JSONObject(result)
                    updateSearchResults(Resource.success(jsonObject.optJSONArray("docs")))
            }
        })
    }

    companion object {

        private const val QUERY_URL = "http://openlibrary.org/search.json?q="

        fun create(activity: SearchActivity): SearchViewModel {
            return ViewModelProviders.of(activity).get(SearchViewModel::class.java)
        }

    }
}

class Resource<T> private constructor(val status: Status, val data: T?, val exception: AppException?) {
    enum class Status {
        SUCCESS, ERROR
    }

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(exception: AppException?): Resource<T> {
            return Resource(Status.ERROR, null, exception)
        }

    }
}

class AppException(val exception: Throwable?): Exception()