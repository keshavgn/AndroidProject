package com.example.keshavanarasappa.androidproject.bottomnavbar

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.keshavanarasappa.androidproject.R
import kotlinx.android.synthetic.main.fragment_edit_image.*
import kotlinx.android.synthetic.main.fragment_edit_image.view.*

class EditImageFragment : Fragment(), SeekBar.OnSeekBarChangeListener {
    interface EditImageFragmentListener {
        fun onBrightnessChanged(brightness: Int)
        fun onSaturationChanged(saturation: Float)
        fun onContrastChanged(contrast: Float)
        fun onEditStarted()
        fun onEditCompleted()
    }

    private var listener: EditImageFragmentListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_image, container, false)
        // keeping brightness value b/w -100 / +100
        view.seekBarBrightness.max = 200
        view.seekBarBrightness.progress = 100

        // keeping contrast value b/w 1.0 - 3.0
        view.seekBarContrast.max = 20
        view.seekBarContrast.progress = 0

        // keeping saturation value b/w 0.0 - 3.0
        view.seekBarSaturation.max = 30
        view.seekBarSaturation.progress = 10

        view.seekBarBrightness.setOnSeekBarChangeListener(this)
        view.seekBarContrast.setOnSeekBarChangeListener(this)
        view.seekBarSaturation.setOnSeekBarChangeListener(this)
        return view
    }

    fun setListener(listener: EditImageFragmentListener) {
        this.listener = listener
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
        var seekBarProgress = progress
        if (seekBar.id == R.id.seekBarBrightness) {
            // brightness values are b/w -100 to +100
            listener?.onBrightnessChanged(seekBarProgress - 100)
        }

        if (seekBar.id == R.id.seekBarContrast) {
            // converting int value to float
            // contrast values are b/w 1.0f - 3.0f
            // progress = progress > 10 ? progress : 10
            seekBarProgress += 10
            val floatVal = .10f * seekBarProgress
            listener?.onContrastChanged(floatVal)
        }

        if (seekBar.id == R.id.seekBarSaturation) {
            // converting int value to float
            // saturation values are b/w 0.0f - 3.0f
            val floatVal = .10f * seekBarProgress
            listener?.onSaturationChanged(floatVal)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        listener?.onEditStarted()
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        listener?.onEditCompleted()
    }

    fun resetControls() {
        seekBarBrightness.progress = 100
        seekBarContrast.progress = 0
        seekBarSaturation.progress = 10
    }
}
