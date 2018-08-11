package com.example.keshavanarasappa.androidproject.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

@Dao
interface PeopleDao {
    @Query("SELECT * FROM people")
    fun fetchPeople(): List<People>

    @Query("SELECT * FROM people WHERE id = :personId LIMIT 1")
    fun fetchPerson(vararg personId: Long): People

    @Insert
    fun insertPeople(vararg people: People)

    @Update
    fun updatePeople(vararg people: People)

    @Query("DELETE from people")
    fun deletePerson()
}

@Dao
interface CarDao {
    @Query("SELECT * FROM car WHERE cid = :id")
    fun fetchCars(vararg id: Long): List<Car>


    @Query("SELECT * FROM car")
    fun fetchAll(): List<Car>

    @Insert
    fun insertCar(vararg car: Car)

    @Insert
    fun insertCars(cars: List<Car>)

    @Query("DELETE from car")
    fun deleteCar()
}