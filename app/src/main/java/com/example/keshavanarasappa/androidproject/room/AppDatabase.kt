package com.example.keshavanarasappa.androidproject.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [(People::class), (Car::class)], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun peopleDao(): PeopleDao
    abstract fun carDao(): CarDao
}