package com.example.keshavanarasappa.androidproject.Recycler

import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by keshava.narasappa on 03/03/18.
 */
class Photo(photoJSON: JSONObject) : Serializable {

    private lateinit var photoDate: String
    lateinit var humanDate: String
        private set
    lateinit var explanation: String
        private set
    lateinit var url: String
        private set

    init {
        try {
            photoDate = photoJSON.getString(PHOTO_DATE)
            humanDate = convertDateToHumanDate()
            explanation = photoJSON.getString(PHOTO_EXPLANATION)
            url = photoJSON.getString(PHOTO_URL)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    private fun convertDateToHumanDate(): String {

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val humanDateFormat = SimpleDateFormat("dd MMMM yyyy")
        val cal = Calendar.getInstance()
        try {
            val parsedDateFormat = dateFormat.parse(photoDate)
            cal.time = parsedDateFormat
        } catch (e: ParseException) {
            e.printStackTrace()
            return ""
        }
        return humanDateFormat.format(cal.time)

    }

    companion object {
        private const val PHOTO_DATE = "date"
        private const val PHOTO_EXPLANATION = "explanation"
        private const val PHOTO_URL = "url"
    }
}
