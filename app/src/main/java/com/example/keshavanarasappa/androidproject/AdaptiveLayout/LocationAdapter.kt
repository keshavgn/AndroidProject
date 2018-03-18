package com.example.keshavanarasappa.androidproject.AdaptiveLayout

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.keshavanarasappa.androidproject.R

/**
 * Created by keshava.narasappa on 17/03/18.
 */
class LocationAdapter(private val context: Context, private val dataSet: List<Location>,
                      private val listener: OnItemClickListener)
  : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

  private var selectedLocation: Location? = null

  var selectedLocationIndex: Int
    get() = dataSet.indexOf(selectedLocation)
    set(index) {
      this.selectedLocation = dataSet[index]
    }

  interface OnItemClickListener {
    fun onItemClick(location: Location)
  }

  class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    var title: TextView = v.findViewById<View>(android.R.id.text1) as TextView
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val view = layoutInflater.inflate(android.R.layout.simple_selectable_list_item, parent, false)

    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.title.text = dataSet[position].name
    holder.title.textSize = 22f
    holder.itemView.setOnClickListener {
      listener.onItemClick(dataSet[position])
      selectedLocation = dataSet[position]
      notifyDataSetChanged()
    }
    if (dataSet[position] == selectedLocation) {
      val backgroundColor = ContextCompat.getColor(context, R.color.color_primary_dark)
      holder.itemView.setBackgroundColor(backgroundColor)
      holder.title.setTextColor(Color.WHITE)
    } else {
      holder.itemView.setBackgroundColor(Color.WHITE)
      holder.title.setTextColor(Color.BLACK)
    }
  }

  override fun getItemCount() = dataSet.size
}
