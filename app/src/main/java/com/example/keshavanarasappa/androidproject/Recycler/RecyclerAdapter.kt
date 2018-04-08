package com.example.keshavanarasappa.androidproject.Recycler
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.keshavanarasappa.androidproject.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*

/**
 * Created by keshava.narasappa on 03/03/18.
 */


class RecyclerAdapter(private val photos: ArrayList<Photo>) : RecyclerView.Adapter<RecyclerAdapter.PhotoHolder>() {
    interface RecyclerOnClickListener {
        fun onClickPhoto(photo: Photo?)
    }

    override fun getItemCount() = photos.size
    var recyclerOnClickListener: RecyclerOnClickListener? = null

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val itemPhoto = photos[position]
        holder.bindPhoto(itemPhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val inflatedView = parent.inflate(R.layout.recyclerview_item_row, false)
        val photoHolder = PhotoHolder(inflatedView)
        photoHolder.recyclerOnClickListener = recyclerOnClickListener
        return photoHolder
    }

    class PhotoHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        var recyclerOnClickListener: RecyclerOnClickListener? = null
        private var view: View = v
        private var photo: Photo? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            recyclerOnClickListener?.onClickPhoto(photo)
        }

        fun bindPhoto(photo: Photo) {
            this.photo = photo
            Picasso.with(view.context).load(photo.url).into(view.itemImage)
            view.itemDate.text = photo.humanDate
            view.itemDescription.text = photo.explanation
        }
    }
}