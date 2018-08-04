package com.example.keshavanarasappa.androidproject.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.keshavanarasappa.androidproject.R

/**
 * Created by keshava.narasappa on 24/02/18.
 */
class MainActivityAdapter internal constructor(private val inflater: LayoutInflater) : BaseAdapter() {
    private val mainList = arrayOf("Search", "Recyler View", "Adaptive UI", "ViewPager, Tab & Fragment", "Maps", "ML Kit with Firebase", "Material Design")

    init {
    }
    override fun getCount(): Int {
        return mainList.count()
    }

    override fun getItem(position: Int): String {
        return mainList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var item = convertView
        val holder: ViewHolder

        if (convertView == null) {

            item = inflater.inflate(R.layout.row_main, parent, false)

            holder = ViewHolder()
            holder.titleTextView = item?.findViewById<TextView>(R.id.text_title)

            item.tag = holder
        } else {
            holder = item?.tag as ViewHolder
        }

        val rowTitle = getItem(position)
        holder.titleTextView?.text = rowTitle
        holder.titleTextView?.setTextSize(2, 25.toFloat())
        holder.titleTextView?.minHeight = 50

        // Set the height of the Item View
        val params = item?.layoutParams
        params?.height = 240
        item?.layoutParams = params
        return item
    }

    private class ViewHolder {
        internal var titleTextView: TextView? = null
    }

}