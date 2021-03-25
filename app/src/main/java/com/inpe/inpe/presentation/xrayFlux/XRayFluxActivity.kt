package com.inpe.inpe.presentation.xrayFlux

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.inpe.inpe.R
import com.inpe.inpe.data.repository.XRayApiDataSource
import kotlinx.android.synthetic.main.activity_x_ray_flux.*

class XRayFluxActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_x_ray_flux)

        val viewModel: XRayFluxViewModel = XRayFluxViewModel.ViewModelFactory(XRayApiDataSource())
            .create(XRayFluxViewModel::class.java)

        viewModel.getXRays()
        viewModel.getXRaysLatestEvent()

        viewModel.xRaysLatestEventData.observe(this, Observer {
            it?.let {
                val first = it.first()
                tv_actually.text = viewModel.getFormattedText(CURRENT, first.timeTag, first.currentClass, first.currentRatio)
                tv_begin.text = viewModel.getFormattedText(BEGINNING, first.beginTime, first.beginClass)
                tv_max.text = viewModel.getFormattedText(MAXIMUM, first.maxTime, first.maxClass, first.maxRatio.toFloatOrNull())
                tv_end.text = viewModel.getFormattedText(END, first.endTime, first.endClass)
            }
        })

        viewModel.xRaysLiveData.observe(this, Observer {
            it?.let {
                val chartView = findViewById<AAChartView>(R.id.chart_xray)

                val chartData = viewModel.getChartModel(it)
                chartView.aa_drawChartWithChartModel(chartData)
//                chartView.aa_refreshChartWithChartModel(chartData)
            }
        })
    }

    companion object {
        const val CURRENT = "Atualmente"
        const val BEGINNING = "Início"
        const val MAXIMUM = "Máximo"
        const val END = "Fim"
    }
}