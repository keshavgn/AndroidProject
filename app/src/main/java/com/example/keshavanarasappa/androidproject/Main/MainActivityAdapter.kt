package com.example.keshavanarasappa.androidproject.Main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.keshavanarasappa.androidproject.R

/**
 * Created by keshava.narasappa on 24/02/18.
 */
class MainActivityAdapter internal constructor(private val context: Context, private val inflater: LayoutInflater) : BaseAdapter() {
    private val mainList = arrayOf("Search", "RecylerView", "Adaptive UI", "ViewPager, Tab & Fragment", "Maps", "ML Kit with Firebase")

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
        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.row_main, parent, false)

            holder = ViewHolder()
            holder.titleTextView = convertView.findViewById<TextView>(R.id.text_title) as TextView

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        val rowTitle = getItem(position)
        holder.titleTextView?.text = rowTitle
        holder.titleTextView?.minHeight = 50
        return convertView
    }

    private class ViewHolder {
        internal var titleTextView: TextView? = null
    }

}