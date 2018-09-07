package com.example.keshavanarasappa.androidproject.recycler

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.keshavanarasappa.androidproject.R
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recyclerview_item_row.*


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

    class PhotoHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), View.OnClickListener, LayoutContainer {
        var recyclerOnClickListener: RecyclerOnClickListener? = null
        private var photo: Photo? = null

        init {
            containerView.setOnClickListener(this)
            containerView.setBackgroundResource(R.drawable.corner_shape)
            val drawable = containerView.background as GradientDrawable
            drawable.setStroke(1, Color.GRAY)
        }

        override fun onClick(v: View) {
            recyclerOnClickListener?.onClickPhoto(photo)
        }

        fun bindPhoto(photo: Photo) {
            this.photo = photo
            Picasso.with(recyclerItem.context).load(photo.url).into(itemImage)
            itemDate.text = photo.humanDate()
            itemDescription.text = photo.explanation
        }
    }
}