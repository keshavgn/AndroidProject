package com.example.keshavanarasappa.androidproject.recycler

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import java.util.*

/**
 * Created by keshava.narasappa on 18/03/18.
 */
class RecyclerViewModel: ViewModel() {
    private var photosList: ArrayList<Photo> = ArrayList()

    fun photos(): ArrayList<Photo> {
        return photosList
    }

    fun numberOfPhotos(): Int {
        return photosList.size
    }

    fun addPhotoToList(photo: Photo) {
        photosList.add(photo)
    }

    fun removePhotoAt(position: Int) {
        photosList.removeAt(position)
    }

    companion object {

        fun create(activity: RecyclerActivity): RecyclerViewModel {
            return ViewModelProviders.of(activity).get(RecyclerViewModel::class.java)
        }

    }
}