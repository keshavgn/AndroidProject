package com.example.keshavanarasappa.androidproject.animations.activities

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import kotlinx.android.synthetic.main.activity_base_animation.*

class FlyWithDogeAnimationActivity : BaseAnimationActivity() {
  override fun onStartAnimation() {
    val positionAnimator = ValueAnimator.ofFloat(0f, -screenHeight)
    positionAnimator.addUpdateListener {
      val value = it.animatedValue as Float
      rocket.translationY = value
      doge.translationY = value
    }

    val rotationAnimator = ValueAnimator.ofFloat(0f, 360f)
    rotationAnimator.addUpdateListener {
      val value = it.animatedValue as Float
      doge.rotation = value
    }

    val animatorSet = AnimatorSet()
    animatorSet.play(positionAnimator).with(rotationAnimator)
    animatorSet.duration = BaseAnimationActivity.Companion.DEFAULT_ANIMATION_DURATION
    animatorSet.start()
  }
}
