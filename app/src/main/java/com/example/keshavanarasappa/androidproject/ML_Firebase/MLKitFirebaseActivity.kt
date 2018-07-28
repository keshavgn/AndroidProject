package com.example.keshavanarasappa.androidproject.ML_Firebase

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.example.keshavanarasappa.androidproject.Main.BaseActivity
import com.example.keshavanarasappa.androidproject.R
import kotlinx.android.synthetic.main.activity_mlkit_firebase.*
import kotlinx.android.synthetic.main.content_mlkit_firebase.*

class MLKitFirebaseActivity : BaseActivity(), MLFirebasePresenter.View {

    private lateinit var presenter: MLFirebasePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mlkit_firebase)

        presenter = MLFirebasePresenter(this)

        cameraButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
        when (requestCode) {
            1 -> if (resultCode == Activity.RESULT_OK) {
                imageReturnedIntent?.data?.let {
                    val selectedImageBitmap = resizeImage(it)
                    imageView.setImageBitmap(selectedImageBitmap)
                    setUpCloudSearch(selectedImageBitmap)
                    overlay.clear()
                    presenter.runTextRecognition(selectedImageBitmap!!)
                    textView.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun setUpCloudSearch(selectedImageBitmap: Bitmap?) {
        cameraButton.setBackgroundResource(R.drawable.ic_cloud_black_24dp)
        cameraButton.setTextColor(getColor(R.color.text_color_primary))
        cameraButton.setOnClickListener {
            overlay.clear()
            presenter.runCloudTextRecognition(selectedImageBitmap!!)
        }
    }

    private fun resizeImage(selectedImage: Uri): Bitmap? {
        return getBitmapFromUri(selectedImage)?.let {
            val scaleFactor = Math.max(
                    it.width.toFloat() / imageView.width.toFloat(),
                    it.height.toFloat() / imageView.height.toFloat())

            Bitmap.createScaledBitmap(it,
                    (it.width / scaleFactor).toInt(),
                    (it.height / scaleFactor).toInt(),
                    true)
        }
    }

    private fun getBitmapFromUri(filePath: Uri): Bitmap? {
        return MediaStore.Images.Media.getBitmap(this.contentResolver, filePath)
    }


    override fun showHandle(text: String, boundingBox: Rect?) {
        overlay.addText(text, boundingBox)
    }

    override fun showBox(boundingBox: Rect?) {
        overlay.addBox(boundingBox)
    }

    override fun showNoTextMessage() {
        Toast.makeText(this, "No text detected", Toast.LENGTH_LONG).show()
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

}
