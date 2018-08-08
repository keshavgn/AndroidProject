package com.example.keshavanarasappa.androidproject.room

import android.app.Application
import android.arch.persistence.room.Room
import android.os.AsyncTask

class RoomDatabaseApplication : Application() {

    companion object {
        var database: AppDatabase? = null
        lateinit var peopleDao: PeopleDao

        fun fetchPeople(): List<People>? {
            val peopleDao = RoomDatabaseApplication.database?.peopleDao()
            return peopleDao?.fetchPeople()
        }
    }

    override fun onCreate() {
        super.onCreate()
        AsyncTask.execute {
            RoomDatabaseApplication.database = Room.databaseBuilder(this, AppDatabase::class.java,"list-master-db").build()
            RoomDatabaseApplication.peopleDao = database!!.peopleDao()
        }
    }

}