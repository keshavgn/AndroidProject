package com.example.keshavanarasappa.androidproject.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "People")
data class People (@ColumnInfo(name="name") var name: String,
                   @ColumnInfo(name="phoneNumber") var phoneNumber: Long,
                   @ColumnInfo(name="city") var city: String,
                   @ColumnInfo(name="id") @PrimaryKey(autoGenerate = true) var id: Long = 0)
