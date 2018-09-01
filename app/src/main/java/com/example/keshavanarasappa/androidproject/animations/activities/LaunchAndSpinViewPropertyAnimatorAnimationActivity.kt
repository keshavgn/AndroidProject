package com.example.keshavanarasappa.androidproject.animations.activities

import kotlinx.android.synthetic.main.activity_base_animation.*

class LaunchAndSpinViewPropertyAnimatorAnimationActivity : BaseAnimationActivity() {
  override fun onStartAnimation() {
    rocket.animate()
            .translationY(-screenHeight)
            .rotationBy(360f)
            .setDuration(BaseAnimationActivity.DEFAULT_ANIMATION_DURATION)
            .start()
  }
}
