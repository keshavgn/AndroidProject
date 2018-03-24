package com.example.keshavanarasappa.androidproject.Recycler

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import com.example.keshavanarasappa.androidproject.R
import kotlinx.android.synthetic.main.activity_recyler.*
import java.io.IOException
import java.util.*

/**
 * Created by keshava.narasappa on 03/03/18.
 */
class RecyclerActivity: AppCompatActivity(), ImageRequester.ImageRequesterResponse {

    private lateinit var imageRequester: ImageRequester
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: RecyclerAdapter
    private lateinit var viewModel: RecyclerViewModel

    private var isGridView = false
    private lateinit var menu: Menu

    private val lastVisibleItemPosition: Int
        get() = if (recyclerView.layoutManager == linearLayoutManager) {
            linearLayoutManager.findLastVisibleItemPosition()
        } else {
            gridLayoutManager.findLastVisibleItemPosition()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyler)

        viewModel = RecyclerViewModel.create(this)
        linearLayoutManager = LinearLayoutManager(this)
        gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = linearLayoutManager

        adapter = RecyclerAdapter(viewModel.photos())
        recyclerView.adapter = adapter

        setRecyclerViewScrollListener()
        setRecyclerViewItemTouchListener()

        imageRequester = ImageRequester(this)
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.numberOfPhotos() == 0) {
            requestPhoto()
        }
    }

    private fun requestPhoto() {
        try {
            imageRequester.getPhoto()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun receivedNewPhoto(newPhoto: Photo) {
        runOnUiThread {
            viewModel.addPhotoToList(newPhoto)
            adapter.notifyItemInserted(viewModel.numberOfPhotos())
        }
    }

    private fun setRecyclerViewScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView!!.layoutManager.itemCount
                if (!imageRequester.isLoadingData && totalItemCount == lastVisibleItemPosition + 1) {
                    imageRequester.photosCount = 20
                    requestPhoto()
                }
            }
        })
    }

    private fun setRecyclerViewItemTouchListener() {

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                viewModel.removePhotoAt(position)
                recyclerView.adapter.notifyItemRemoved(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            if (isGridView) {
                menu.getItem(0).setIcon(R.drawable.grid)
            } else {
                menu.getItem(0).setIcon(R.drawable.list)
            }
            changeLayoutManager()
            isGridView = !isGridView
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeLayoutManager() {
        if (recyclerView.layoutManager == linearLayoutManager) {
            recyclerView.layoutManager = gridLayoutManager
            if (viewModel.numberOfPhotos() == 1) {
                requestPhoto()
            }
        } else {
            recyclerView.layoutManager = linearLayoutManager
        }
    }
}
