package com.example.keshavanarasappa.androidproject.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "People")
public final data class People (@ColumnInfo(name = "name") var name: String = "",
                                @ColumnInfo(name = "phoneNumber") var phoneNumber: Long = 0,
                                @ColumnInfo(name = "city") var city: String = "",
                                @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0)

@Entity(tableName = "Car")
data class Car (@ColumnInfo(name = "name") var name: String = "",
                @ColumnInfo(name = "model") var model: String = "",
                @ColumnInfo(name = "year") var year: String = "",
                @ColumnInfo(name = "cid") var cid: Long = 0,
                @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0)
