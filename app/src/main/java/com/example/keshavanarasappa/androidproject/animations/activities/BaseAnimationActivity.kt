package com.example.keshavanarasappa.androidproject.animations.activities

import android.os.Bundle
import android.util.DisplayMetrics
import com.example.keshavanarasappa.androidproject.R
import com.example.keshavanarasappa.androidproject.common.BaseActivity
import kotlinx.android.synthetic.main.activity_base_animation.*

abstract class BaseAnimationActivity : BaseActivity() {
  protected var screenHeight = 0f

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_base_animation)

    frameLayout.setOnClickListener { onStartAnimation() }
  }

  override fun onResume() {
    super.onResume()

    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    screenHeight = displayMetrics.heightPixels.toFloat()
  }

  protected abstract fun onStartAnimation()

  companion object {
    val DEFAULT_ANIMATION_DURATION = 2500L
  }
}
