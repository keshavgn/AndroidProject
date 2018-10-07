package com.example.keshavanarasappa.androidproject.viewpager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keshavanarasappa.androidproject.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie.*
/**
 * Created by keshava.narasappa on 08/04/18.
 */
class MovieFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movie, container, false)
        val args = arguments
        titleTextView.text = args?.getString(MovieHelper.KEY_TITLE)
        ratingTextView.text = String.format("%d/10", args?.getInt(MovieHelper.KEY_RATING))
        overviewTextView.text = args?.getString(MovieHelper.KEY_OVERVIEW)

        Picasso.with(activity)
                .load(resources.getIdentifier(args?.getString(MovieHelper.KEY_POSTER_URI), "drawable", activity?.packageName))
                .into(posterImageView)

        return view
    }

    companion object {

        fun newInstance(movie: Movie): MovieFragment {
            val args = Bundle()
            args.putString(MovieHelper.KEY_TITLE, movie.title)
            args.putInt(MovieHelper.KEY_RATING, movie.rating)
            args.putString(MovieHelper.KEY_POSTER_URI, movie.posterUri)
            args.putString(MovieHelper.KEY_OVERVIEW, movie.overview)

            val fragment = MovieFragment()
            fragment.arguments = args
            return fragment
        }
    }

}