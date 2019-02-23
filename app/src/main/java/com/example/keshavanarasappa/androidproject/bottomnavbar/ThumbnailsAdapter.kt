package com.example.keshavanarasappa.androidproject.bottomnavbar

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keshavanarasappa.androidproject.R
import com.zomato.photofilters.imageprocessors.Filter
import com.zomato.photofilters.utils.ThumbnailItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.thumbnail_list_item.*
import org.jetbrains.anko.backgroundColor


class ThumbnailsAdapter(context: Context, thumbnailItemList: MutableList<ThumbnailItem>, listener: ThumbnailsAdapterListener): RecyclerView.Adapter<ThumbnailsAdapter.MyViewHolder>() {

    interface ThumbnailsAdapterListener {
        fun onFilterSelected(filter: Filter)
    }

    private var thumbnailItemList: List<ThumbnailItem>? = null
    private var listener: ThumbnailsAdapterListener? = null
    private var mContext: Context? = null
    private var selectedIndex = 0

    init {
        mContext = context
        this.thumbnailItemList = thumbnailItemList
        this.listener = listener
    }

    inner class MyViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        init {
        }

        fun bindData(item: ThumbnailItem, position: Int) {
            thumbnail!!.setImageBitmap(item.image)

            thumbnail!!.setOnClickListener {
                item.filter?.let { listener?.onFilterSelected(it) }
                selectedIndex = position
                notifyDataSetChanged()
            }

            filterName!!.text = item.filterName

            mContext?.let {
                if (selectedIndex == position) {
                    filterName!!.setTextColor(ContextCompat.getColor(it, R.color.filter_label_selected))
                    containerView.backgroundColor = mContext?.resources!!.getColor(R.color.light_green)
                } else {
                    filterName!!.setTextColor(ContextCompat.getColor(it, R.color.filter_label_normal))
                    containerView.backgroundColor = mContext?.resources!!.getColor(R.color.text_color_primary)
                }
            }
        }
    }

    override fun getItemCount(): Int = thumbnailItemList?.count() ?: 0

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val thumbnailItem = thumbnailItemList?.get(position)
        thumbnailItem?.let { holder.bindData(it, position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.thumbnail_list_item, parent, false)
        return MyViewHolder(itemView)
    }
}