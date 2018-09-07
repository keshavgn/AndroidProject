package com.example.keshavanarasappa.androidproject.recycler

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by keshava.narasappa on 03/03/18.
 */
data class Photo(@SerializedName("date") val date: String,
                 @SerializedName("explanation") val explanation: String,
                 @SerializedName("url") val url: String): Serializable {

    fun humanDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val humanDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val cal = Calendar.getInstance()
        try {
            val parsedDateFormat = dateFormat.parse(date)
            cal.time = parsedDateFormat
        } catch (e: ParseException) {
            e.printStackTrace()
            return ""
        }
        return humanDateFormat.format(cal.time)
    }
}
