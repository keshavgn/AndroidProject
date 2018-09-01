package com.example.keshavanarasappa.androidproject.animations

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.abc_search_dropdown_item_icons_2line.*

class AnimationsAdapter(private val context: Context, private val items: List<AnimationItem>) :
        RecyclerView.Adapter<AnimationsAdapter.RocketViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return RocketViewHolder(view)
    }

    override fun onBindViewHolder(holder: RocketViewHolder, position: Int) {
        holder.text1.text = items[position].title
        holder.setTitleOnClickListener(context, items)
    }

    class RocketViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun setTitleOnClickListener(context: Context, items: List<AnimationItem>) {
            text1.setOnClickListener { context.startActivity(items[adapterPosition].intent) }
        }
    }
}

class AnimationItem(val title: String, val intent: Intent)