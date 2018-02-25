package com.example.keshavanarasappa.androidproject

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.support.v7.widget.ShareActionProvider
import android.view.View

import com.example.keshavanarasappa.androidproject.R
import com.squareup.picasso.Picasso

/**
 * Created by keshava.narasappa on 24/02/18.
 */
class DetailActivity : AppCompatActivity() {
    internal var imageURL = ""
    internal var shareActionProvider: ShareActionProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Tell the activity which XML layout is right
        setContentView(R.layout.activity_detail)

        // Enable the "Up" button for more navigation options
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Access the imageview from XML
        val imageView = findViewById<View>(R.id.img_cover) as ImageView

        // 13. unpack the coverId from its trip inside your Intent
        val coverId = this.intent.extras!!.getString("coverID")

        // See if there is a valid coverId

        if (coverId != null && coverId.length > 0) {

            // Use the ID to construct an image URL
            imageURL = IMAGE_URL_BASE + coverId + "-L.jpg"

            // Use Picasso to load the image
            Picasso.with(this)
                    .load(imageURL)
                    .placeholder(R.drawable.img_books_loading)
                    .into(imageView)
        }
    }

    private fun setShareIntent() {

        // create an Intent with the contents of the TextView
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Book Recommendation!")
        shareIntent.putExtra(Intent.EXTRA_TEXT, imageURL)

        // Make sure the provider knows
        // it should work with that Intent
        if (shareActionProvider != null) {
            shareActionProvider!!.setShareIntent(shareIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        // Inflate the menu
        // this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        // Access the Share Item defined in menu XML
        val shareItem = menu.findItem(R.id.menu_item_share)

        // Access the object responsible for
        // putting together the sharing submenu
        if (shareItem != null) {
            shareActionProvider = MenuItemCompat.getActionProvider(shareItem) as ShareActionProvider
        }

        setShareIntent()

        return true
    }

    companion object {

        private val IMAGE_URL_BASE = "http://covers.openlibrary.org/b/id/"
    }
}
