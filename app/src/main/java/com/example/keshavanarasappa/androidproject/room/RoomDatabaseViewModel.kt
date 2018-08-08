package com.example.keshavanarasappa.androidproject.room

import android.util.Log
import com.example.keshavanarasappa.androidproject.R

data class RoomDatabaseViewModel(val people: People) {
    private val highlightColors = arrayOf(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent,
            R.color.primaryLightColor, R.color.secondaryLightColor, R.color.secondaryDarkColor)

    fun getHighlightLetter(): String {
        return people.name.first().toString()
    }

    fun getHighlightLetterColor(): Int {
        val uniqueIdMultiplier = getHighlightLetter().hashCode().div(6)
        val colorArrayIndex = getHighlightLetter().hashCode() - (uniqueIdMultiplier * 6)
        Log.i("color", colorArrayIndex.toString())
        return (highlightColors[colorArrayIndex])
    }

}