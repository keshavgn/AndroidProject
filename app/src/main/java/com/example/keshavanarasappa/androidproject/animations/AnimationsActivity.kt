package com.example.keshavanarasappa.androidproject.animations

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import com.example.keshavanarasappa.androidproject.R
import com.example.keshavanarasappa.androidproject.animations.activities.*
import com.example.keshavanarasappa.androidproject.common.BaseActivity
import kotlinx.android.synthetic.main.activity_animations.*
import java.util.*



class AnimationsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animations)

        recyclerView.layoutManager = android.support.v7.widget.LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))

        val items = ArrayList<AnimationItem>()

        items.add(AnimationItem(getString(R.string.title_launch_rocket),
                Intent(this, LaunchRocketValueAnimatorAnimationActivity::class.java)))

        items.add(AnimationItem(getString(R.string.title_spin_rocket),
                Intent(this, RotateRocketAnimationActivity::class.java)))

        items.add(AnimationItem(getString(R.string.title_accelerate_rocket),
                Intent(this, AccelerateRocketAnimationActivity::class.java)))

        items.add(AnimationItem(getString(R.string.title_launch_rocket_objectanimator),
                Intent(this, LaunchRocketObjectAnimatorAnimationActivity::class.java)))

        items.add(AnimationItem(getString(R.string.title_color_animation),
                Intent(this, ColorAnimationActivity::class.java)))

        items.add(AnimationItem(getString(R.string.launch_spin),
                Intent(this, LaunchAndSpinAnimatorSetAnimationActivity::class.java)))

        items.add(AnimationItem(getString(R.string.launch_spin_viewpropertyanimator),
                Intent(this, LaunchAndSpinViewPropertyAnimatorAnimationActivity::class.java)))

        items.add(AnimationItem(getString(R.string.title_with_doge),
                Intent(this, FlyWithDogeAnimationActivity::class.java)))

        items.add(AnimationItem(getString(R.string.title_animation_events),
                Intent(this, WithListenerAnimationActivity::class.java)))

        items.add(AnimationItem(getString(R.string.title_there_and_back),
                Intent(this, FlyThereAndBackAnimationActivity::class.java)))

        items.add(AnimationItem(getString(R.string.title_jump_and_blink),
                Intent(this, XmlAnimationActivity::class.java)))

        recyclerView.adapter = AnimationsAdapter(this, items)
    }
}
