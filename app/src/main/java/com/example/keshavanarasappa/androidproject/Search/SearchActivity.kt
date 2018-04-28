package com.example.keshavanarasappa.androidproject.Search

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ShareActionProvider
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import kotlinx.android.synthetic.main.search_activity.*
import org.json.JSONArray
import android.arch.lifecycle.Observer
import com.example.keshavanarasappa.androidproject.R

/**
 * Created by keshava.narasappa on 24/02/18.
 */
class SearchActivity: AppCompatActivity(), View.OnClickListener, AdapterView.OnItemClickListener  {

    internal lateinit var jsonAdapterGrid: JSONAdapter
    internal lateinit var shareActionProvider: ShareActionProvider
    internal lateinit var sharedPreferences: SharedPreferences
    internal lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.search_activity)

        mainButton.setOnClickListener(this)
        mainButton.text = "Search"

        jsonAdapterGrid = JSONAdapter(this, layoutInflater)
        mainGridview.onItemClickListener = this
        mainGridview.adapter = jsonAdapterGrid

        viewModel = SearchViewModel.create(this)

        viewModel.getSearchResults().observe(this, Observer<Resource<JSONArray>> { searchResults ->
            if (searchResults != null) {
                when (searchResults.status) {
                    Resource.Status.SUCCESS -> {
                        jsonAdapterGrid.updateData(searchResults.data!!)
                        Toast.makeText(applicationContext, "Success!", Toast.LENGTH_LONG).show()
                    }
                    Resource.Status.ERROR -> {
                        Toast.makeText(this, "Error: "+searchResults.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })


        displayWelcome()
    }

    private fun displayWelcome() {

        sharedPreferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE)

        val name = sharedPreferences.getString(PREF_NAME, "")

        if (name.isNotEmpty()) {
            Toast.makeText(this, "Welcome back, $name!", Toast.LENGTH_LONG).show()
        }
        else {

            val alert = AlertDialog.Builder(this)
            alert.setTitle("Hello!")
            alert.setMessage("What is your name?")

            val input = EditText(this)
            alert.setView(input)

            alert.setPositiveButton("OK") { _, _ ->
                val inputName = input.text.toString()

                val e = sharedPreferences.edit()
                e.putString(PREF_NAME, inputName)
                e.apply()

                Toast.makeText(applicationContext, "Welcome, $inputName!", Toast.LENGTH_LONG).show()
            }

            alert.setNegativeButton("Cancel") { _, _ -> }
            alert.show()
        }
    }

    override fun onClick(v: View) {
        searchBooks()
    }

    private fun searchBooks() {
        viewModel.queryBooks(mainEdittext.text.toString())

        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {

        val coverID = jsonAdapterGrid.getItem(position).optString("cover_i", "")

        val detailIntent = Intent(this, DetailActivity::class.java)

        detailIntent.putExtra("coverID", coverID)
        startActivity(detailIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    companion object {

        private const val PREFS = "prefs"
        private const val PREF_NAME = "name"
    }
}