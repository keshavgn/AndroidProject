package com.example.keshavanarasappa.androidproject.room

import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.example.keshavanarasappa.androidproject.R
import com.example.keshavanarasappa.androidproject.common.BaseActivity
import com.example.keshavanarasappa.androidproject.databinding.ActivityRoomDatabaseBinding
import com.example.keshavanarasappa.androidproject.databinding.ContentPeopleBinding

class RoomDatabaseActivity : BaseActivity(), NewContactFragment.OnFragmentInteractionListener {

    private lateinit var peopleBinding: ActivityRoomDatabaseBinding
    private lateinit var contentBinding: ContentPeopleBinding
    private lateinit var roomDatabaseAdapter: RoomDatabaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        peopleBinding = DataBindingUtil.setContentView(this, R.layout.activity_room_database)

        AsyncTask.execute {
            RoomDatabaseApplication.fetchPeople()?.let {
                runOnUiThread { setupRecyclerAdapter(it) }
            }
        }
    }

    private fun setupRecyclerAdapter(people: List<People>) {
        val recyclerViewLinearLayoutManager = LinearLayoutManager(this)
        contentBinding = peopleBinding.contentLists!!
        contentBinding.peopleRecyclerView.layoutManager = recyclerViewLinearLayoutManager
        roomDatabaseAdapter = RoomDatabaseAdapter(people, this)
        contentBinding.peopleRecyclerView.adapter = roomDatabaseAdapter
    }

    private fun updateContacts() {
        AsyncTask.execute {
            RoomDatabaseApplication.fetchPeople()?.let {
                roomDatabaseAdapter.people = it
                runOnUiThread { roomDatabaseAdapter.notifyDataSetChanged() }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.room, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_room) {
            createNewContact()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createNewContact() {
        val manager = fragmentManager
        NewContactFragment.newInstance().show(manager, "searchingDialog")
    }

    override fun onFragmentInteraction(name: String, phoneNumber: Long, city: String) {
        AsyncTask.execute {
            val person = People(name, phoneNumber, city, phoneNumber)
            RoomDatabaseApplication.peopleDao.insertPeople(person)
            updateContacts()
        }
    }
}
