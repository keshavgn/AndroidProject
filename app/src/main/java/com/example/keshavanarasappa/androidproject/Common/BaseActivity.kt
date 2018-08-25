package com.example.keshavanarasappa.androidproject.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.example.keshavanarasappa.androidproject.R

open class BaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ab = actionBar
        ab?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId != R.id.menu_item && item?.itemId != R.menu.share && item?.itemId != R.id.menu_room) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}