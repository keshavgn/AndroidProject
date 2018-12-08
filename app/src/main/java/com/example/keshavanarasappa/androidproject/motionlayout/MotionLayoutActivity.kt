package com.example.keshavanarasappa.androidproject.motionlayout

import android.os.Bundle
import com.example.keshavanarasappa.androidproject.R
import com.example.keshavanarasappa.androidproject.common.BaseActivity
import kotlinx.android.synthetic.main.activity_motion_layout.*

class MotionLayoutActivity : BaseActivity() {
    private val adapter = OnboardingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_layout)

        val data = OnboardingPage
                .values()
                .map { onboardingPageData ->
                    val pageView = OnboardingPageView(this)
                    pageView.setPageData(onboardingPageData)

                    pageView
                }

        adapter.setData(data)
        onboardingView.setAdapter(adapter)
    }
}
