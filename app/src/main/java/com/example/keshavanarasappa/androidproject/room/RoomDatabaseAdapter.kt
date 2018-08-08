package com.example.keshavanarasappa.androidproject.room

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.keshavanarasappa.androidproject.databinding.RowPersonBinding

data class RoomDatabaseAdapter(var people: List<People>, private val context: RoomDatabaseActivity) : RecyclerView.Adapter<RoomDatabaseViewHolder>() {

    override fun getItemCount() = people.size

    override fun onBindViewHolder(holder: RoomDatabaseViewHolder, position: Int) {
        holder.setPeopleItem(people[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomDatabaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val holderListCategoryItemBinding = RowPersonBinding.inflate(layoutInflater, parent, false)
        return RoomDatabaseViewHolder(holderListCategoryItemBinding, context)
    }
}

data class RoomDatabaseViewHolder(private val holderListCategoryBinding: RowPersonBinding,
                                  private val listCategoriesActivity: RoomDatabaseActivity) :
        RecyclerView.ViewHolder(holderListCategoryBinding.root) {

    fun setPeopleItem(people: People) {
        val listCategoryViewModel = RoomDatabaseViewModel(people)
        holderListCategoryBinding.peopleItem = listCategoryViewModel
        holderListCategoryBinding.executePendingBindings()
    }
}