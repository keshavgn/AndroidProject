package com.example.keshavanarasappa.androidproject.bottomnavbar

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View


class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
            outRect.left = space
            outRect.right = 0
        } else {
            outRect.right = space
            outRect.left = 0
        }
    }
}