package com.example.keshavanarasappa.androidproject.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface PeopleDao {
    @Query("SELECT * FROM people")
    fun fetchPeople(): List<People>

    @Insert
    fun insertPeople(vararg people: People)
}