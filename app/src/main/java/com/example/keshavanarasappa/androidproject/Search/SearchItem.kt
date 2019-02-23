package com.example.keshavanarasappa.androidproject.search

import com.google.gson.annotations.SerializedName

data class SearchItem(@SerializedName("author_name") val authorNames: List<String>,
                      @SerializedName("title") val title: String,
                      @SerializedName("cover_i") val coverId: String? = null)
