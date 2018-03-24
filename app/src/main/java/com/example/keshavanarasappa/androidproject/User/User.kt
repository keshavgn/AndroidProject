package com.example.keshavanarasappa.androidproject.User

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by keshava.narasappa on 24/03/18.
 */
open class User: RealmObject() {

    @PrimaryKey lateinit var emailId: String
    lateinit var password: String
    lateinit var sex: String
    lateinit var dob: String
    var status: Boolean = false
}