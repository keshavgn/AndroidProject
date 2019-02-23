package com.example.keshavanarasappa.androidproject.recycler

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.Z
import android.view.ViewGroup
import com.example.keshavanarasappa.androidproject.R
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recyclerview_item_row.*
import android.widget.LinearLayout




/**
 * Created by keshava.narasappa on 03/03/18.
 */


class RecyclerAdapter(private val photos: ArrayList<Photo>) : RecyclerView.Adapter<RecyclerAdapter.PhotoHolder>() {
    interface RecyclerOnClickListener {
        fun onClickPhoto(photo: Photo?)
    }

    override fun getItemCount() = photos.size
    var recyclerOnClickListener: RecyclerOnClickListener? = null
    var columns = 1

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val itemPhoto = photos[position]
        holder.bindPhoto(itemPhoto, position, photos.size, columns)
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

        fun bindPhoto(photo: Photo, index: Int, totalItems: Int, columns: Int) {
            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            val top = if (index < columns) 20 else 10
            val bottom = if ((totalItems - columns) > index) 10 else 20
            if (columns == 1) {
                layoutParams.setMargins(20, top, 20, bottom)
            } else {
                val mod = index.rem(2)
                val left = if (mod == 0) 20 else 10
                val right = if (mod == 1) 20 else 10
                layoutParams.setMargins(left, top, right, bottom)
            }

            containerView.layoutParams = layoutParams
            this.photo = photo
            Picasso.with(recyclerItem.context).load(photo.url).into(itemImage)
            itemDate.text = photo.humanDate()
            itemDescription.text = photo.explanation
        }
    }
}