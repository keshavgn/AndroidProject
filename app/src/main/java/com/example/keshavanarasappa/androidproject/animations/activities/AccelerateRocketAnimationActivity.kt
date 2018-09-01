package com.example.keshavanarasappa.androidproject.animations.activities

import android.animation.ValueAnimator
import android.view.animation.AccelerateInterpolator
import kotlinx.android.synthetic.main.activity_base_animation.*

class AccelerateRocketAnimationActivity : BaseAnimationActivity() {
  override fun onStartAnimation() {
    val valueAnimator = ValueAnimator.ofFloat(0f, -screenHeight)
    valueAnimator.addUpdateListener {
      val value = it.animatedValue as Float
      rocket.translationY = value
    }

    valueAnimator.interpolator = AccelerateInterpolator(1.5f)
    valueAnimator.duration = BaseAnimationActivity.DEFAULT_ANIMATION_DURATION

    valueAnimator.start()
  }
}
