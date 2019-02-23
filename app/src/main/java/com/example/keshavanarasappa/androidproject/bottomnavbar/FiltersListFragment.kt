package com.example.keshavanarasappa.androidproject.bottomnavbar

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keshavanarasappa.androidproject.R
import com.zomato.photofilters.FilterPack
import com.zomato.photofilters.imageprocessors.Filter
import com.zomato.photofilters.utils.ThumbnailItem
import com.zomato.photofilters.utils.ThumbnailsManager
import kotlinx.android.synthetic.main.fragment_filters_list.view.*


class FiltersListFragment : Fragment(), ThumbnailsAdapter.ThumbnailsAdapterListener {
    private var mAdapter: ThumbnailsAdapter? = null

    private lateinit var thumbnailItemList: MutableList<ThumbnailItem>

    private var listener: FiltersListFragmentListener? = null

    fun setListener(listener: FiltersListFragmentListener) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_filters_list, container, false)

        context?.let {
            thumbnailItemList = arrayListOf()
            mAdapter = ThumbnailsAdapter(it, thumbnailItemList, this)
        }

        val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        view.recyclerView.layoutManager = mLayoutManager
        view.recyclerView.itemAnimator = DefaultItemAnimator()
        val space = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f,
                resources.displayMetrics).toInt()
        view.recyclerView.addItemDecoration(SpacesItemDecoration(space))
        view.recyclerView.adapter = mAdapter

        prepareThumbnail(null)

        return view
    }

    /**
     * Renders thumbnails in horizontal list
     * loads default image from Assets if passed param is null
     *
     * @param bitmap
     */
    fun prepareThumbnail(bitmap: Bitmap?) {
        val r = Runnable {
            val thumbImage: Bitmap?

            if (bitmap == null) {
                thumbImage = BitmapUtils.getBitmapFromAssets(context!!, BottomNavbarActivity.IMAGE_NAME, 100, 100)
            } else {
                thumbImage = Bitmap.createScaledBitmap(bitmap, 100, 100, false)
            }

            if (thumbImage == null)
                return@Runnable

            ThumbnailsManager.clearThumbs()
            thumbnailItemList.clear()

            // add normal bitmap first
            val thumbnailItem = ThumbnailItem()
            thumbnailItem.image = thumbImage
            thumbnailItem.filterName = getString(R.string.filter_normal)
            ThumbnailsManager.addThumb(thumbnailItem)

            val filters = FilterPack.getFilterPack(activity)

            for (filter in filters) {
                val tI = ThumbnailItem()
                tI.image = thumbImage
                tI.filter = filter
                tI.filterName = filter.name
                ThumbnailsManager.addThumb(tI)
            }

            thumbnailItemList.addAll(ThumbnailsManager.processThumbs(activity))

            activity?.runOnUiThread(Runnable { mAdapter?.notifyDataSetChanged() })
        }

        Thread(r).start()
    }

    override fun onFilterSelected(filter: Filter) {
        if (listener != null)
            listener!!.onFilterSelected(filter)
    }

    interface FiltersListFragmentListener {
        fun onFilterSelected(filter: Filter)
    }
}