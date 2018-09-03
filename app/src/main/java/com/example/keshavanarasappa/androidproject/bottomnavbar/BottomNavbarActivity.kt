package com.example.keshavanarasappa.androidproject.bottomnavbar

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.keshavanarasappa.androidproject.R
import com.example.keshavanarasappa.androidproject.common.BaseActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.zomato.photofilters.imageprocessors.Filter
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter
import kotlinx.android.synthetic.main.activity_bottom_navbar.*
import org.jetbrains.anko.backgroundColor
import java.util.*


class BottomNavbarActivity : BaseActivity(), FiltersListFragment.FiltersListFragmentListener, EditImageFragment.EditImageFragmentListener {
    companion object {
        const val IMAGE_NAME = "dog.jpg"
        const val SELECT_GALLERY_IMAGE = 101
    }

    private var originalImage: Bitmap? = null
    // to backup image with filter applied
    private var filteredImage: Bitmap? = null

    // the final image after applying : brightness, saturation, contrast
    private var finalImage: Bitmap? = null

    private var filtersListFragment: FiltersListFragment? = null
    private var editImageFragment: EditImageFragment? = null

    // modified image values
    private var brightnessFinal = 0
    private var saturationFinal = 1.0f
    private var contrastFinal = 1.0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navbar)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        System.loadLibrary("NativeImageProcessor")
        loadImage()
        setupViewPager(viewPager)

    }

    private fun loadImage() {
        originalImage = BitmapUtils.getBitmapFromAssets(this, IMAGE_NAME, 300, 300)
        filteredImage = originalImage?.copy(Bitmap.Config.ARGB_8888, true)
        finalImage = originalImage?.copy(Bitmap.Config.ARGB_8888, true)
        imagePreview.setImageBitmap(originalImage)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)

        // adding filter list fragment
        filtersListFragment = FiltersListFragment()
        filtersListFragment?.setListener(this)

        // adding edit image fragment
        editImageFragment = EditImageFragment()
        editImageFragment?.setListener(this)

        filtersListFragment?.let { adapter.addFragment(it, getString(R.string.tab_filters)) }
        editImageFragment?.let { adapter.addFragment(it, getString(R.string.tab_edit)) }

        viewPager.adapter = adapter
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getCount(): Int {
            return mFragmentList.count()
        }

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }

    override fun onFilterSelected(filter: Filter) {
        // reset image controls
        resetControls()

        // applying the selected filter
        filteredImage = originalImage?.copy(Bitmap.Config.ARGB_8888, true)
        // preview filtered image
        imagePreview.setImageBitmap(filter.processFilter(filteredImage))

        finalImage = filteredImage?.copy(Bitmap.Config.ARGB_8888, true)
    }

    override fun onBrightnessChanged(brightness: Int) {
        brightnessFinal = brightness
        val myFilter = Filter()
        myFilter.addSubFilter(BrightnessSubFilter(brightness))
        imagePreview.setImageBitmap(myFilter.processFilter(finalImage?.copy(Bitmap.Config.ARGB_8888, true)))
    }

    override fun onSaturationChanged(saturation: Float) {
        saturationFinal = saturation
        val myFilter = Filter()
        myFilter.addSubFilter(SaturationSubfilter(saturation))
        imagePreview.setImageBitmap(myFilter.processFilter(finalImage?.copy(Bitmap.Config.ARGB_8888, true)))
    }

    override fun onContrastChanged(contrast: Float) {
        contrastFinal = contrast
        val myFilter = Filter()
        myFilter.addSubFilter(ContrastSubFilter(contrast))
        imagePreview.setImageBitmap(myFilter.processFilter(finalImage?.copy(Bitmap.Config.ARGB_8888, true)))
    }

    override fun onEditStarted() {

    }

    override fun onEditCompleted() {
        // once the editing is done i.e seekBar is drag is completed,
        // apply the values on to filtered image
        val bitmap = filteredImage?.copy(Bitmap.Config.ARGB_8888, true)

        val myFilter = Filter()
        myFilter.addSubFilter(BrightnessSubFilter(brightnessFinal))
        myFilter.addSubFilter(ContrastSubFilter(contrastFinal))
        myFilter.addSubFilter(SaturationSubfilter(saturationFinal))
        finalImage = myFilter.processFilter(bitmap)
    }

    /**
     * Resets image edit controls to normal when new filter
     * is selected
     */
    private fun resetControls() {
        if (editImageFragment != null) {
            editImageFragment?.resetControls()
        }
        brightnessFinal = 0
        saturationFinal = 1.0f
        contrastFinal = 1.0f
    }


    private fun openImageFromGallery() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if (report.areAllPermissionsGranted()) {
                            val intent = Intent(Intent.ACTION_PICK)
                            intent.type = "image/*"
                            startActivityForResult(intent, SELECT_GALLERY_IMAGE)
                        } else {
                            Toast.makeText(applicationContext, "Permissions are not granted!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?, token: PermissionToken?) {
                        token?.continuePermissionRequest()
                    }
                }).check()
    }

    /*
    * saves image to camera gallery
    */
    private fun saveImageToGallery() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if (report.areAllPermissionsGranted()) {
                            val path = BitmapUtils.insertImage(contentResolver, finalImage, System.currentTimeMillis().toString() + "_profile.jpg", "")
                            if (!TextUtils.isEmpty(path)) {
                                val snackbar = Snackbar
                                        .make(container, "Image saved to gallery!", Snackbar.LENGTH_LONG)
                                        .setAction("OPEN", object : View.OnClickListener {
                                            override fun onClick(view: View) {
                                                openImage(path)
                                            }
                                        })

                                snackbar.show()
                            } else {
                                val snackBar = Snackbar.make(container, "Unable to save image!", Snackbar.LENGTH_LONG)
                                snackBar.show()
                            }
                        } else {
                            Toast.makeText(applicationContext, "Permissions are not granted!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?, token: PermissionToken?) {
                        token?.continuePermissionRequest()
                    }
                }).check()

    }

    // opening image in default image viewer app
    private fun openImage(path: String?) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.setDataAndType(Uri.parse(path), "image/*")
        startActivity(intent)
    }

    private val mOnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.navigation_filters -> {
                    viewPager.currentItem = 0
                    navigation.backgroundColor = resources.getColor(R.color.bgBottomNavigation)
                    return true
                }
                R.id.navigation_edit -> {
                    viewPager.currentItem = 1
                    navigation.backgroundColor = resources.getColor(R.color.green)
                    return true
                }
            }
            return false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.photo, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if (id == R.id.action_open) {
            openImageFromGallery()
            return true
        } else if (id == R.id.action_save) {
            saveImageToGallery()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_GALLERY_IMAGE) {
            data?.data?.let { val bitmap = BitmapUtils.getBitmapFromGallery(this, it, 800, 800)

                // clear bitmap memory
                originalImage?.recycle()
                finalImage?.recycle()
                finalImage?.recycle()

                originalImage = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                filteredImage = originalImage?.copy(Bitmap.Config.ARGB_8888, true)
                finalImage = originalImage?.copy(Bitmap.Config.ARGB_8888, true)
                imagePreview.setImageBitmap(originalImage)
                bitmap.recycle()

                // render selected image thumbnails
                filtersListFragment?.prepareThumbnail(originalImage)
            }
        }
    }

}
