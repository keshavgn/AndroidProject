package com.example.keshavanarasappa.androidproject.animations.activities

import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import kotlinx.android.synthetic.main.activity_base_animation.*

class LaunchRocketValueAnimatorAnimationActivity : BaseAnimationActivity() {
  override fun onStartAnimation() {
    val valueAnimator = ValueAnimator.ofFloat(0f, -screenHeight)
    valueAnimator.addUpdateListener {
      val value = it.animatedValue as Float
      rocket.translationY = value
    }

    valueAnimator.interpolator = LinearInterpolator()
    valueAnimator.duration = BaseAnimationActivity.Companion.DEFAULT_ANIMATION_DURATION

    valueAnimator.start()
  }
}
