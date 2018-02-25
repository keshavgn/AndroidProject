package com.example.keshavanarasappa.androidproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * Created by keshava.narasappa on 24/02/18.
 */
class MainActivityAdapter internal constructor(private val context: Context, private val inflater: LayoutInflater) : BaseAdapter() {
    private val mainList = arrayOf("Search")

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

            convertView = inflater.inflate(R.layout.row_main, null)

            holder = ViewHolder()
            holder.titleTextView = convertView.findViewById<TextView>(R.id.text_title) as TextView

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        val rowTitle = getItem(position)
        holder.titleTextView?.text = rowTitle

        return convertView
    }

    private class ViewHolder {
        internal var titleTextView: TextView? = null
    }

}