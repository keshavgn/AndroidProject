package com.example.keshavanarasappa.androidproject.animations.activities

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import kotlinx.android.synthetic.main.activity_base_animation.*

class LaunchAndSpinAnimatorSetAnimationActivity : BaseAnimationActivity() {
  override fun onStartAnimation() {
    val positionAnimator = ValueAnimator.ofFloat(0f, -screenHeight)

    positionAnimator.addUpdateListener {
      val value = it.animatedValue as Float
      rocket.translationY = value
    }

    val rotationAnimator = ObjectAnimator.ofFloat(rocket, "rotation", 0f, 300f)
    val animatorSet = AnimatorSet()
    animatorSet.play(positionAnimator).with(rotationAnimator)
    animatorSet.duration = BaseAnimationActivity.DEFAULT_ANIMATION_DURATION
    animatorSet.start()
  }
}
