package com.example.keshavanarasappa.androidproject.search

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.ShareActionProvider
import android.view.Menu
import android.view.MenuItem
import com.example.keshavanarasappa.androidproject.R
import com.example.keshavanarasappa.androidproject.common.BaseActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * Created by keshava.narasappa on 24/02/18.
 */
class DetailActivity : BaseActivity() {
    private var imageURL = ""
    private var shareActionProvider: ShareActionProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        val coverId = this.intent.extras!!.getString("coverID")

        if (!coverId.isEmpty()) {
            imageURL = IMAGE_URL_BASE.plus(coverId).plus("-L.jpg")
            Picasso.with(this).load(imageURL).placeholder(R.drawable.img_books_loading).into(imageView)
        }
    }

    private fun setShareIntent() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Book Recommendation!")
        shareIntent.putExtra(Intent.EXTRA_TEXT, imageURL)
        startActivity(shareIntent)
//        if (shareActionProvider != null) {
//            shareActionProvider!!.setShareIntent(shareIntent)
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.share, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_share) {
            setShareIntent()
//            shareActionProvider = MenuItemCompat.getActionProvider(shareItem) as? ShareActionProvider
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val IMAGE_URL_BASE = "http://covers.openlibrary.org/b/id/"
    }
}
