package com.example.keshavanarasappa.androidproject.animations.activities

import android.animation.ObjectAnimator
import kotlinx.android.synthetic.main.activity_base_animation.*

class LaunchRocketObjectAnimatorAnimationActivity : BaseAnimationActivity() {
  override fun onStartAnimation() {
    val objectAnimator = ObjectAnimator.ofFloat(rocket, "translationY", 0f, -screenHeight)
    objectAnimator.duration = BaseAnimationActivity.DEFAULT_ANIMATION_DURATION
    objectAnimator.start()
  }
}
