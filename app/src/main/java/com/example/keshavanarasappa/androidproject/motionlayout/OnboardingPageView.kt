package com.example.keshavanarasappa.androidproject.motionlayout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.keshavanarasappa.androidproject.R
import kotlinx.android.synthetic.main.item_onboarding_page.view.*

class OnboardingPageView : FrameLayout {

  private lateinit var page: OnboardingPage

  constructor(context: Context?) : super(context)

  constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

  constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

  init {
    initUi()
  }

  private fun initUi() {
    LayoutInflater.from(context).inflate(R.layout.item_onboarding_page, this, true)
  }

  fun setPageData(onboardingPage: OnboardingPage) {
    this.page = onboardingPage

    platformLogo.setImageResource(onboardingPage.logoResource)
    pageTitle.text = resources.getString(page.titleResource)
    pageDescription.text = resources.getString(page.descriptionResource)
  }
}