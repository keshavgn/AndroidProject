package com.example.keshavanarasappa.androidproject.materialdesign

import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.keshavanarasappa.androidproject.R
import com.example.keshavanarasappa.androidproject.common.BaseActivity
import kotlinx.android.synthetic.main.activity_material_design.*
import kotlinx.android.synthetic.main.activity_material_design_detail.*

class MaterialDesignActivity : BaseActivity() {
    private lateinit var staggeredLayoutManager: StaggeredGridLayoutManager
    private lateinit var adapter: MaterialDesignAdapter
    private lateinit var menu: Menu
    private var isListView: Boolean = false

    private val onItemClickListener = object : MaterialDesignAdapter.OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            val intent = MaterialDesignDetailActivity.newIntent(this@MaterialDesignActivity, position)

            val imagePair = Pair(placeImage as View, "tImage")
            val holderPair = Pair(placeNameHolder as View, "tNameHolder")

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MaterialDesignActivity, imagePair, holderPair)
            ActivityCompat.startActivity(this@MaterialDesignActivity, intent, options.toBundle())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_design)
        isListView = true

        staggeredLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = staggeredLayoutManager
        adapter = MaterialDesignAdapter(this)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(onItemClickListener)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == R.id.action_toggle) {
            toggle()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toggle() {
        if (isListView) {
            showGridView()
        } else {
            showListView()
        }
    }

    private fun showListView() {
        val item = menu.findItem(R.id.action_toggle)
        item.setIcon(R.drawable.ic_action_grid)
        item.title = getString(R.string.show_as_grid)
        isListView = true
        staggeredLayoutManager.spanCount = 1
    }

    private fun showGridView() {
        val item = menu.findItem(R.id.action_toggle)
        item.setIcon(R.drawable.ic_action_list)
        item.title = getString(R.string.show_as_list)
        isListView = false
        staggeredLayoutManager.spanCount = 2
    }

}
