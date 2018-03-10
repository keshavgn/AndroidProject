/**
 * Created by keshava.narasappa on 24/02/18.
 */

package com.example.keshavanarasappa.androidproject

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    internal lateinit var mainButton: Button
    internal lateinit var mainListView: ListView
    internal lateinit var mainAdapter: MainActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        mainListView = findViewById<View>(R.id.main_listview) as ListView

        mainListView.onItemClickListener = this
        mainAdapter = MainActivityAdapter(this, layoutInflater)
        mainListView.adapter = mainAdapter
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        if (position == 0) {
            val searchIntent = Intent(this, SearchActivity::class.java)
            startActivity(searchIntent)
        } else if (position == 1) {
            val recyclerIntent = Intent(this, RecyclerActivity::class.java)
            startActivity(recyclerIntent)
        }
    }
}