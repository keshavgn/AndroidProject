package com.example.keshavanarasappa.androidproject.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.keshavanarasappa.androidproject.R

import com.squareup.picasso.Picasso

import org.json.JSONArray
import org.json.JSONObject
/**
 * Created by keshava.narasappa on 24/02/18.
 */
class JSONAdapter internal constructor(private val context: Context,
                                       private val inflater: LayoutInflater) : BaseAdapter() {
    var jsonArray: JSONArray? = null

    init {
        jsonArray = JSONArray()
    }

    internal fun updateData(jsonArray: JSONArray) {

        // update the adapter's dataset
        this.jsonArray = jsonArray
        notifyDataSetChanged()
    }

    override fun getCount(): Int = jsonArray?.length() ?: 0

    override fun getItem(position: Int): JSONObject = jsonArray?.optJSONObject(position) ?: JSONObject()

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val holder: ViewHolder

        // check if the view already exists
        // if so, no need to inflate and findViewById again!
        if (convertView == null) {

            // Inflate the custom row layout from your XML.
            convertView = inflater.inflate(R.layout.row_book, null)

            // create a new "Holder" with subviews
            holder = ViewHolder()
            holder.thumbnailImageView = convertView!!
                    .findViewById<View>(R.id.img_thumbnail) as ImageView
            holder.titleTextView = convertView
                    .findViewById<View>(R.id.text_title) as TextView
            holder.authorTextView = convertView
                    .findViewById<View>(R.id.text_author) as TextView

            // hang onto this holder for future recyclage
            convertView.tag = holder
        } else {

            // skip all the expensive inflation/findViewById
            // and just get the holder you already made
            holder = convertView.tag as ViewHolder
        }

        // Get the current book's data in JSON form
        val jsonObject = getItem(position)

        // See if there is a cover ID in the Object
        if (jsonObject.has(COVER_I)) {

            // If so, grab the Cover ID out from the object
            val imageID = jsonObject.optString(COVER_I)

            // Construct the image URL (specific to API)
            val imageURL = (IMAGE_URL_BASE + imageID + IMAGE_URL_EXT)

            // Use Picasso to load the image
            // Temporarily have a placeholder in case it's slow to load
            Picasso.with(context)
                    .load(imageURL)
                    .placeholder(R.drawable.ic_books)
                    .into(holder.thumbnailImageView)
        } else {

            // If there is no cover ID in the object, use a placeholder
            holder.thumbnailImageView?.setImageResource(R.drawable.ic_books)
        }

        // Grab the title and author from the JSON
        var bookTitle = ""
        var authorName = ""

        if (jsonObject.has(TITLE)) {
            bookTitle = jsonObject.optString(TITLE)
        }

        if (jsonObject.has(AUTHOR_NAME)) {
            authorName = jsonObject.optJSONArray(AUTHOR_NAME)
                    .optString(0)
        }

        // Send these Strings to the TextViews for display
        holder.titleTextView?.text = bookTitle
        holder.authorTextView?.text = authorName

        return convertView
    }

    // this is used so you only ever have to do
    // inflation and finding by ID once ever per View
    private class ViewHolder {
        internal var thumbnailImageView: ImageView? = null
        internal var titleTextView: TextView? = null
        internal var authorTextView: TextView? = null
    }

    companion object {
        private const val IMAGE_URL_BASE = "http://covers.openlibrary.org/b/id/"
        private const val IMAGE_URL_EXT = "-S.jpg"
        private const val AUTHOR_NAME = "author_name"
        private const val TITLE = "title"
        private const val COVER_I = "cover_i"
    }
}

