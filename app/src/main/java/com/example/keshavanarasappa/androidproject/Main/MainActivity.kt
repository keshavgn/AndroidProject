/**
 * Created by keshava.narasappa on 24/02/18.
 */

package com.example.keshavanarasappa.androidproject.Main

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import com.example.keshavanarasappa.androidproject.AdaptiveLayout.AdaptiveLayoutActivity
import com.example.keshavanarasappa.androidproject.ML_Firebase.MLKitFirebaseActivity
import com.example.keshavanarasappa.androidproject.Maps.MapsActivity
import com.example.keshavanarasappa.androidproject.MaterialDesign.MaterialDesignActivity
import com.example.keshavanarasappa.androidproject.R
import com.example.keshavanarasappa.androidproject.Recycler.RecyclerActivity
import com.example.keshavanarasappa.androidproject.Search.SearchActivity
import com.example.keshavanarasappa.androidproject.User.LoginActivity
import com.example.keshavanarasappa.androidproject.User.RealmManager
import com.example.keshavanarasappa.androidproject.ViewPager.ViewPagerActivity
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
        mainAdapter = MainActivityAdapter(this, layoutInflater)
        mainListView.adapter = mainAdapter

        RealmManager.instance.initializeRealmConfig(applicationContext)

        val isFirstLaunch = this.getPreferences(Context.MODE_PRIVATE).getBoolean(getString(R.string.first_launch), true)

        if (isFirstLaunch) {
            val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
                putBoolean(getString(R.string.first_launch), false)
                commit()
            }

            TapTargetView.showFor(this,
                    TapTarget.forBounds(Rect(10, 10, 10, 300), getString(R.string.logged_in),
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

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        if (position == 0) {
            val searchIntent = Intent(this, SearchActivity::class.java)
            startActivity(searchIntent)
        } else if (position == 1) {
            val recyclerIntent = Intent(this, RecyclerActivity::class.java)
            recyclerIntent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            startActivity(recyclerIntent)
        } else if (position == 2) {
            val adaptiveUIIntent = Intent(this, AdaptiveLayoutActivity::class.java)
            startActivity(adaptiveUIIntent)
        } else if (position == 3) {
            val viewPagerIntent = Intent(this, ViewPagerActivity::class.java)
            startActivity(viewPagerIntent)
        } else if (position == 4) {
            val mapsIntent = Intent(this, MapsActivity::class.java)
            startActivity(mapsIntent)
        } else if (position == 5) {
            val mlkitIntent = Intent(this, MLKitFirebaseActivity::class.java)
            startActivity(mlkitIntent)
        } else if (position == 6) {
            val materialDesignIntent = Intent(this, MaterialDesignActivity::class.java)
            startActivity(materialDesignIntent)
        }
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