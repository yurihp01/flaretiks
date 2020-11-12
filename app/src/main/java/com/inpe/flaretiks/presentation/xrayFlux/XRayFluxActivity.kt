package com.inpe.flaretiks.presentation.xrayFlux

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.inpe.flaretiks.R
import com.inpe.flaretiks.data.repository.XRayApiDataSource
import kotlinx.android.synthetic.main.activity_x_ray_flux.*
import java.math.RoundingMode

class XRayFluxActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_x_ray_flux)

        val viewModel: XRayFluxViewModel = XRayFluxViewModel.ViewModelFactory(XRayApiDataSource())
            .create(XRayFluxViewModel::class.java)

        viewModel.xRaysLatestEventData.observe(this, Observer {
            it?.let {
                val first = it.first()
                tv_actually.text = viewModel.getFormattedText(CURRENT, first.timeTag, first.currentClass, first.currentRatio)
                tv_begin.text = viewModel.getFormattedText(BEGINNING, first.beginTime, first.beginClass)
                tv_max.text = viewModel.getFormattedText(MAXIMUM, first.maxTime, first.maxClass, first.maxRatio.toFloat())
                tv_end.text = viewModel.getFormattedText(END, first.endTime, first.endClass)
            }
        })

        viewModel.getXRays()
        viewModel.getXRaysLatestEvent()

//        viewModel.viewFlipperLiveData.observe(this, Observer {
//            it?.let {
//                view
//            }
//        })
    }

    companion object {
        const val CURRENT = "Atualmente"
        const val BEGINNING = "Início"
        const val MAXIMUM = "Máximo"
        const val END = "Fim"
    }
}