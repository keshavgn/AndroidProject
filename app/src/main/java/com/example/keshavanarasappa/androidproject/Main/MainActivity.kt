/**
 * Created by keshava.narasappa on 24/02/18.
 */

package com.example.keshavanarasappa.androidproject.Main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import com.example.keshavanarasappa.androidproject.AdaptiveLayout.AdaptiveLayoutActivity
import com.example.keshavanarasappa.androidproject.Maps.MapsActivity
import com.example.keshavanarasappa.androidproject.R
import com.example.keshavanarasappa.androidproject.Recycler.RecyclerActivity
import com.example.keshavanarasappa.androidproject.Search.SearchActivity
import com.example.keshavanarasappa.androidproject.User.LoginActivity
import com.example.keshavanarasappa.androidproject.User.RealmManager
import com.example.keshavanarasappa.androidproject.ViewPager.ViewPagerActivity

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private lateinit var mainButton: Button
    private lateinit var mainListView: ListView
    private lateinit var mainAdapter: MainActivityAdapter

    private var isLoggedIn = false
    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        mainListView = findViewById<View>(R.id.main_listview) as ListView

        mainListView.onItemClickListener = this
        mainAdapter = MainActivityAdapter(this, layoutInflater)
        mainListView.adapter = mainAdapter

        RealmManager.instance.initializeRealmConfig(applicationContext)
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