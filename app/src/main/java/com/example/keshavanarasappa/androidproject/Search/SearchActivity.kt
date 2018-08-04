package com.example.keshavanarasappa.androidproject.search

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import com.example.keshavanarasappa.androidproject.R
import com.example.keshavanarasappa.androidproject.common.BaseActivity
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.progress_bar.*
import org.json.JSONArray

/**
 * Created by keshava.narasappa on 24/02/18.
 */
class SearchActivity: BaseActivity(), View.OnClickListener, AdapterView.OnItemClickListener  {

    private lateinit var jsonAdapterGrid: JSONAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)

        mainButton.setOnClickListener(this)

        jsonAdapterGrid = JSONAdapter(this, layoutInflater)
        mainGridview.onItemClickListener = this
        mainGridview.adapter = jsonAdapterGrid

        viewModel = SearchViewModel.create(this)

        viewModel.getSearchResults().observe(this, Observer<Resource<JSONArray>> { searchResults ->
            if (searchResults != null) {
                progressBar(show = false)
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
            Toast.makeText(this, WELCOME_BACK.plus(", $name!"), Toast.LENGTH_LONG).show()
        }
        else {

            val alert = AlertDialog.Builder(this)
            alert.setTitle(ALERT_TITLE)
            alert.setMessage(ALERT_MESSAGE)

            val input = EditText(this)
            alert.setView(input)

            alert.setPositiveButton(OK) { _, _ ->
                val inputName = input.text.toString()

                val e = sharedPreferences.edit()
                e.putString(PREF_NAME, inputName)
                e.apply()

                Toast.makeText(applicationContext, WELCOME.plus(", $inputName!"), Toast.LENGTH_LONG).show()
            }

            alert.setNegativeButton(CANCEL) { _, _ -> }
            alert.show()
        }
    }

    override fun onClick(v: View) {
        searchBooks()
    }

    private fun searchBooks() {
        progressBar(show = true)
        viewModel.queryBooks(mainEdittext.text.toString())

        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {

        val coverID = jsonAdapterGrid.getItem(position).optString(COVER_I, "")

        val detailIntent = Intent(this, DetailActivity::class.java)

        detailIntent.putExtra(COVERID, coverID)
        startActivity(detailIntent)
    }

    private fun progressBar(show: Boolean) {
        progressBar.visibility = if (show == true) View.VISIBLE else View.GONE
    }

    companion object {
        private const val PREFS = "prefs"
        private const val PREF_NAME = "name"
        private const val COVER_I = "cover_i"
        private const val WELCOME = "Welcome"
        private const val WELCOME_BACK = "Welcome back"
        private const val ALERT_TITLE = "Hello!"
        private const val OK ="OK"
        private const val CANCEL ="Cancel"
        private const val COVERID ="coverID"
        private const val ALERT_MESSAGE ="What is your name?"
    }
}