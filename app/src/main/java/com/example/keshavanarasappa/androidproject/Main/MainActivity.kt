/**
 * Created by keshava.narasappa on 24/02/18.
 */

package com.example.keshavanarasappa.androidproject.main

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import com.example.keshavanarasappa.androidproject.R
import com.example.keshavanarasappa.androidproject.adaptivelayout.AdaptiveLayoutActivity
import com.example.keshavanarasappa.androidproject.animations.AnimationsActivity
import com.example.keshavanarasappa.androidproject.maps.MapsActivity
import com.example.keshavanarasappa.androidproject.materialdesign.MaterialDesignActivity
import com.example.keshavanarasappa.androidproject.mlfirebase.MLKitFirebaseActivity
import com.example.keshavanarasappa.androidproject.recycler.RecyclerActivity
import com.example.keshavanarasappa.androidproject.room.RoomDatabaseActivity
import com.example.keshavanarasappa.androidproject.search.SearchActivity
import com.example.keshavanarasappa.androidproject.tabbar.TabbarActivity
import com.example.keshavanarasappa.androidproject.user.LoginActivity
import com.example.keshavanarasappa.androidproject.user.RealmManager
import com.example.keshavanarasappa.androidproject.videoplayer.VideoPlayerActivity
import com.example.keshavanarasappa.androidproject.viewpager.ViewPagerActivity
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private lateinit var mainAdapter: MainActivityAdapter
    private var isLoggedIn = false
    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        mainListView.onItemClickListener = this
        mainAdapter = MainActivityAdapter(layoutInflater)
        mainListView.adapter = mainAdapter

        RealmManager.instance.initializeRealmConfig(applicationContext)

        val isFirstLaunch = this.getPreferences(Context.MODE_PRIVATE).getBoolean(getString(R.string.first_launch), true)

        if (isFirstLaunch) {
            val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
                putBoolean(getString(R.string.first_launch), false)
                apply()
            }

            TapTargetView.showFor(this,
                    TapTarget.forBounds(Rect(getLeftPosition(), 0, 0, 300), getString(R.string.logged_in),
                            getString(R.string.description_login))
                            .cancelable(false)
                            .tintTarget(true),
                    object : TapTargetView.Listener() {
                        override fun onTargetClick(view: TapTargetView) {
                            super.onTargetClick(view)
                            view.dismiss(true)
                        }
                    })
        }
    }

    private fun getLeftPosition(): Int {
        return (Resources.getSystem().displayMetrics.widthPixels - 100) * 2
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        var intent = Intent()
        when(position) {
            0 -> {
                intent = Intent(this, SearchActivity::class.java)
            }
            1 -> {
                val recyclerIntent = Intent(this, RecyclerActivity::class.java)
                recyclerIntent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                intent = recyclerIntent
            }
            2 -> {
                intent = Intent(this, AdaptiveLayoutActivity::class.java)
            }
            3 -> {
                intent = Intent(this, ViewPagerActivity::class.java)
            }
            4 -> {
                intent = Intent(this, MapsActivity::class.java)
            }
            5 -> {
                intent = Intent(this, MLKitFirebaseActivity::class.java)
            }
            6 -> {
                intent = Intent(this, MaterialDesignActivity::class.java)
            }
            7 -> {
                intent = Intent(this, RoomDatabaseActivity::class.java)
            }
            8 -> {
                intent = Intent(this, AnimationsActivity::class.java)
            }
            9 -> {
                intent = Intent(this, VideoPlayerActivity::class.java)
            }
            10 -> {
                intent = Intent(this, TabbarActivity::class.java)
            }
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.user_status, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            if (isLoggedIn) {
                menu.getItem(0).title = "Login"
                isLoggedIn = false
            } else {
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivityForResult(loginIntent,1)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 200) {
            menu.getItem(0).title = "Logout"
            isLoggedIn = true
        }
    }
}