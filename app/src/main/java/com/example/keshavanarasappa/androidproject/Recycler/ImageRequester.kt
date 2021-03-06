package com.example.keshavanarasappa.androidproject.recycler

/**
 * Created by keshava.narasappa on 03/03/18.
 */
import android.app.Activity
import android.content.Context
import android.net.Uri.Builder
import com.example.keshavanarasappa.androidproject.R
import com.google.gson.Gson
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ImageRequester(listeningActivity: Activity) {

    interface ImageRequesterResponse {
        fun receivedNewPhoto(newPhoto: Photo)
    }

    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val responseListener: ImageRequesterResponse
    private val context: Context
    private val client: OkHttpClient
    var photosCount = 20
    var isLoadingData: Boolean = false
        private set

    init {
        responseListener = listeningActivity as ImageRequesterResponse
        context = listeningActivity.applicationContext
        client = OkHttpClient()
    }

    fun getPhoto() {

        val date = dateFormat.format(calendar.time)

        val urlRequest = Builder().scheme(URL_SCHEME)
                .authority(URL_AUTHORITY)
                .appendPath(URL_PATH_1)
                .appendPath(URL_PATH_2)
                .appendQueryParameter(URL_QUERY_PARAM_DATE_KEY, date)
                .appendQueryParameter(URL_QUERY_PARAM_API_KEY, context.getString(R.string.api_key))
                .build().toString()

        val request = Request.Builder().url(urlRequest).build()
        isLoadingData = true

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                isLoadingData = false
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {

                try {
                    val photoJSON = JSONObject(response.body()?.string())

                    calendar.add(Calendar.DAY_OF_YEAR, -1)

                    if (photoJSON.getString(MEDIA_TYPE_KEY) != MEDIA_TYPE_VIDEO_VALUE) {
                        val receivedPhoto = Gson().fromJson(photoJSON.toString(), Photo::class.java)
                        responseListener.receivedNewPhoto(receivedPhoto)
                        if (photosCount == 0) {
                            isLoadingData = false
                        } else {
                            getPhoto()
                            photosCount -= 1
                        }
                    } else {
                        getPhoto()
                    }
                } catch (e: JSONException) {
                    isLoadingData = false
                    e.printStackTrace()
                }

            }
        })
    }

    companion object {
        private const val MEDIA_TYPE_KEY = "media_type"
        private const val MEDIA_TYPE_VIDEO_VALUE = "video"
        private const val URL_SCHEME = "https"
        private const val URL_AUTHORITY = "api.nasa.gov"
        private const val URL_PATH_1 = "planetary"
        private const val URL_PATH_2 = "apod"
        private const val URL_QUERY_PARAM_DATE_KEY = "date"
        private const val URL_QUERY_PARAM_API_KEY = "api_key"
    }
}