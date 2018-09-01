package com.example.keshavanarasappa.androidproject.bottomnavbar

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.example.keshavanarasappa.androidproject.R
import com.example.keshavanarasappa.androidproject.common.BaseActivity
import com.example.keshavanarasappa.androidproject.tabbar.FirstTabFragment
import com.example.keshavanarasappa.androidproject.tabbar.SecondTabFragment
import kotlinx.android.synthetic.main.activity_bottom_navbar.*


class BottomNavbarActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navbar)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        loadFragment(FirstTabFragment())
    }

    private val mOnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.navigation_shop -> {
                    loadFragment(FirstTabFragment())
                    return true
                }
                R.id.navigation_cart -> {
                    loadFragment(SecondTabFragment())
                    return true
                }
            }
            return false
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
