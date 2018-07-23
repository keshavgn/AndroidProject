package com.example.keshavanarasappa.androidproject.Recycler

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import com.example.keshavanarasappa.androidproject.Main.BaseActivity
import com.example.keshavanarasappa.androidproject.R
import kotlinx.android.synthetic.main.activity_recyler.*
import java.io.IOException

/**
 * Created by keshava.narasappa on 03/03/18.
 */
class RecyclerActivity: BaseActivity(), ImageRequester.ImageRequesterResponse, RecyclerAdapter.RecyclerOnClickListener {

    private lateinit var imageRequester: ImageRequester
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: RecyclerAdapter
    private lateinit var viewModel: RecyclerViewModel

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
        adapter.recyclerOnClickListener = this as RecyclerAdapter.RecyclerOnClickListener

        setRecyclerViewScrollListener()
        setRecyclerViewItemTouchListener()

        imageRequester = ImageRequester(this)

        val index = this.getPreferences(Context.MODE_PRIVATE).getInt(getString(R.string.layout_index), 1)
        changeLayoutManager(index)
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
            adapter.notifyDataSetChanged()
        }
    }

    override fun onClickPhoto(photo: Photo?) {
        val showPhotoIntent = Intent(this, PhotoActivity::class.java)
        showPhotoIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        showPhotoIntent.putExtra(PHOTO_KEY, photo)
        startActivity(showPhotoIntent)
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
        val index = this.getPreferences(Context.MODE_PRIVATE).getInt(getString(R.string.layout_index), 1)
        this.menu = menu
        if (index == 1) {
            menuInflater.inflate(R.menu.main, menu)
        } else {
            menuInflater.inflate(R.menu.main2, menu)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var index = 1
        if (item?.itemId == R.id.menu_item) {
            if (recyclerView.layoutManager == gridLayoutManager) {
                menu.getItem(0).setIcon(R.drawable.grid)
            } else {
                menu.getItem(0).setIcon(R.drawable.list)
                index = 2
            }
            changeLayoutManager(index = index)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeLayoutManager(index: Int = 1) {
        val layoutManager = recyclerView.layoutManager
        if (index == 2) {
            recyclerView.layoutManager = gridLayoutManager
            if (viewModel.numberOfPhotos() == 1) {
                requestPhoto()
            }
        } else {
            recyclerView.layoutManager = linearLayoutManager
        }

        if (recyclerView.layoutManager != layoutManager) {
            val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
                putInt(getString(R.string.layout_index), index)
                commit()
            }
        }
    }


    companion object {
        private const val PHOTO_KEY = "PHOTO"
    }
}
