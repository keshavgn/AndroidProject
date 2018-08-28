package com.example.keshavanarasappa.androidproject.animations.activities

import android.animation.ValueAnimator
import kotlinx.android.synthetic.main.activity_base_animation.*

class FlyThereAndBackAnimationActivity : BaseAnimationActivity() {
  override fun onStartAnimation() {
    val animator = ValueAnimator.ofFloat(0f, -screenHeight)

    animator.addUpdateListener {
      val value = it.animatedValue as Float
      rocket.translationY = value
      doge.translationY = value
    }

    animator.repeatMode = ValueAnimator.REVERSE
    animator.repeatCount = 3
    animator.duration = 500L
    animator.start()
  }
}
