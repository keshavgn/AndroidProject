package com.example.keshavanarasappa.androidproject.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [(People::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun peopleDao(): PeopleDao
}