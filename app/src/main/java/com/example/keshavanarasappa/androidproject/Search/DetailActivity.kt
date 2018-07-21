package com.example.keshavanarasappa.androidproject.Search

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.view.Menu
import android.support.v7.widget.ShareActionProvider
import com.example.keshavanarasappa.androidproject.Main.BaseActivity
import com.example.keshavanarasappa.androidproject.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * Created by keshava.narasappa on 24/02/18.
 */
class DetailActivity : BaseActivity() {
    internal var imageURL = ""
    internal var shareActionProvider: ShareActionProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        val coverId = this.intent.extras!!.getString("coverID")

        if (coverId != null && coverId.length > 0) {
            imageURL = IMAGE_URL_BASE + coverId + "-L.jpg"
            Picasso.with(this).load(imageURL).placeholder(R.drawable.img_books_loading).into(imageView)
        }
    }

    private fun setShareIntent() {

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Book Recommendation!")
        shareIntent.putExtra(Intent.EXTRA_TEXT, imageURL)

        if (shareActionProvider != null) {
            shareActionProvider!!.setShareIntent(shareIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main, menu)

        val shareItem = menu.findItem(R.id.menu_item)
        if (shareItem != null) {
            shareActionProvider = MenuItemCompat.getActionProvider(shareItem) as? ShareActionProvider
        }

        setShareIntent()

        return true
    }

    companion object {

        private val IMAGE_URL_BASE = "http://covers.openlibrary.org/b/id/"
    }
}
