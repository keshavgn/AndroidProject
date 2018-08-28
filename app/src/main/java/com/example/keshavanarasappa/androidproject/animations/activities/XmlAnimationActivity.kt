package com.example.keshavanarasappa.androidproject.animations.activities

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import com.example.keshavanarasappa.androidproject.R
import kotlinx.android.synthetic.main.activity_base_animation.*

class XmlAnimationActivity : BaseAnimationActivity() {
  override fun onStartAnimation() {
    val rocketAnimatorSet = AnimatorInflater.loadAnimator(this, R.animator.jump_and_blink) as AnimatorSet
    rocketAnimatorSet.setTarget(rocket)

    val dogeAnimatorSet = AnimatorInflater.loadAnimator(this, R.animator.jump_and_blink) as AnimatorSet
    dogeAnimatorSet.setTarget(doge)

    val bothAnimatorSet = AnimatorSet()
    bothAnimatorSet.playTogether(rocketAnimatorSet, dogeAnimatorSet)
    bothAnimatorSet.duration = BaseAnimationActivity.DEFAULT_ANIMATION_DURATION
    bothAnimatorSet.start()
  }
}
