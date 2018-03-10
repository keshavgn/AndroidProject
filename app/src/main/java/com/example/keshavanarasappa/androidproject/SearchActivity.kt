package com.example.keshavanarasappa.androidproject

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ShareActionProvider
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import kotlinx.android.synthetic.main.search_activity.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.io.Serializable
import android.arch.lifecycle.Observer

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

        jsonAdapterGrid = JSONAdapter(this, layoutInflater)
        mainGridview.onItemClickListener = this
        mainGridview.adapter = jsonAdapterGrid

        viewModel = SearchViewModel.create(this)
        val data = viewModel.getSearchResults()

        viewModel.getSearchResults().observe(this, Observer<Resource<JSONArray>> { searchResults ->
            if (searchResults != null) {
                when (searchResults.status) {
                    Resource.Status.SUCCESS -> {

                        jsonAdapterGrid.updateData(searchResults.data!!)
                        Toast.makeText(applicationContext, "Success!", Toast.LENGTH_LONG).show()
                    }
                    Resource.Status.ERROR->{
                        Toast.makeText(this, "Error: "+searchResults.exception?.message, Toast.LENGTH_LONG);
                    }
                }
            }
        })


        displayWelcome()
    }

    fun displayWelcome() {

        sharedPreferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE)

        val name = sharedPreferences.getString(PREF_NAME, "")

        if (name.length > 0) {

            Toast.makeText(this, "Welcome back, $name!", Toast.LENGTH_LONG).show()
        } else {

            val alert = AlertDialog.Builder(this)
            alert.setTitle("Hello!")
            alert.setMessage("What is your name?")

            val input = EditText(this)
            alert.setView(input)

            alert.setPositiveButton("OK") { dialog, whichButton ->
                val inputName = input.text.toString()

                val e = sharedPreferences.edit()
                e.putString(PREF_NAME, inputName)
                e.apply()

                Toast.makeText(applicationContext, "Welcome, $inputName!", Toast.LENGTH_LONG).show()
            }

            alert.setNegativeButton("Cancel") { dialog, whichButton -> }

            alert.show()
        }
    }

    override fun onClick(v: View) {
        searchBooks()
    }

    fun searchBooks() {
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

        private val PREFS = "prefs"
        private val PREF_NAME = "name"
    }
}