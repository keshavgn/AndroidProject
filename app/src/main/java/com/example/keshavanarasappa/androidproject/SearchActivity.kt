package com.example.keshavanarasappa.androidproject

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ShareActionProvider
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

/**
 * Created by keshava.narasappa on 24/02/18.
 */
class SearchActivity: AppCompatActivity(), View.OnClickListener, AdapterView.OnItemClickListener  {
    internal lateinit var mainTextView: TextView
    internal lateinit var mainButton: Button
    internal lateinit var mainEditText: EditText
    internal lateinit var mainListView: ListView
    internal lateinit var jsonAdapter: JSONAdapter
    internal lateinit var shareActionProvider: ShareActionProvider
    internal lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.search_activity)

        mainTextView = findViewById<View>(R.id.main_textview) as TextView
        mainButton = findViewById<View>(R.id.main_button) as Button
        mainButton.setOnClickListener(this)

        mainEditText = findViewById<View>(R.id.main_edittext) as EditText

        mainListView = findViewById<View>(R.id.main_listview) as ListView
        mainListView.onItemClickListener = this
        jsonAdapter = JSONAdapter(this, layoutInflater)
        mainListView.adapter = jsonAdapter

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

        queryBooks(mainEditText.text.toString())

        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {

        val coverID = jsonAdapter.getItem(position).optString("cover_i", "")

        val detailIntent = Intent(this, DetailActivity::class.java)

        detailIntent.putExtra("coverID", coverID)
        startActivity(detailIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main, menu)

        val shareItem = menu.findItem(R.id.menu_item_share)

        if (shareItem != null) {
            shareActionProvider = MenuItemCompat.getActionProvider(shareItem) as ShareActionProvider
        }

        setShareIntent()

        return true
    }

    private fun setShareIntent() {

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Android Development")
        shareIntent.putExtra(Intent.EXTRA_TEXT, mainTextView.text)

        shareActionProvider.setShareIntent(shareIntent)
    }

    private fun queryBooks(searchString: String) {

        var urlString = ""
        try {
            urlString = URLEncoder.encode(searchString, "UTF-8")
        } catch (e: UnsupportedEncodingException) {

            e.printStackTrace()
            Toast.makeText(this, "Error: " + e.message, Toast.LENGTH_LONG).show()
        }

        val client = AsyncHttpClient()

        client.get(QUERY_URL + urlString, object : JsonHttpResponseHandler() {

            override fun onSuccess(jsonObject: JSONObject?) {

                Toast.makeText(applicationContext, "Success!", Toast.LENGTH_LONG).show()

                jsonAdapter.updateData(jsonObject!!.optJSONArray("docs"))
            }

            override fun onFailure(statusCode: Int, throwable: Throwable, error: JSONObject) {

                Toast.makeText(applicationContext, "Error: " + statusCode + " "
                        + throwable.message, Toast.LENGTH_LONG).show()

                Log.e("omg android", statusCode.toString() + " " + throwable.message)
            }
        })
    }

    companion object {

        private val PREFS = "prefs"
        private val PREF_NAME = "name"
        private val QUERY_URL = "http://openlibrary.org/search.json?q="
    }
}