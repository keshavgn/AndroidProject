package com.example.keshavanarasappa.androidproject.Common

import android.content.Context
import org.json.JSONObject
import java.io.IOException

/**
 * Created by keshava.narasappa on 08/04/18.
 */
object JsonHelper {
    fun loadJsonFromFile(filename: String, context: Context): String {
        var json = ""

        try {
            val input = context.assets.open(filename)
            val size = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            input.close()
            json = buffer.toString(Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return json
    }
}