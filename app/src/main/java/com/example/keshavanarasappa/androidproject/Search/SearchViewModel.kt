package com.example.keshavanarasappa.androidproject.Search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import org.json.JSONArray
import com.example.keshavanarasappa.androidproject.Search.Resource.Status.*


/**
 * Created by keshava.narasappa on 10/03/18.
 */
public class SearchViewModel: ViewModel() {

    private val searchResults = MutableLiveData<Resource<JSONArray>>()

    fun updateSearchResults(item: Resource<JSONArray>) {
        searchResults.setValue(item)
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

        val client = AsyncHttpClient()

        client.get(QUERY_URL + urlString, object : JsonHttpResponseHandler() {

            override fun onSuccess(jsonObject: JSONObject?) {
                updateSearchResults(Resource.success(jsonObject!!.optJSONArray("docs")))
            }

            override fun onFailure(statusCode: Int, throwable: Throwable, error: JSONObject) {
                updateSearchResults(Resource.error(AppException(throwable)))
            }
        })
    }

    companion object {

        private val QUERY_URL = "http://openlibrary.org/search.json?q="

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

class AppException(val exceptin: Throwable?): Exception();