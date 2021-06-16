package com.inpe.inpe.presentation.xrayFlux

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartZoomType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.inpe.inpe.R
import com.inpe.inpe.data.model.XRay
import com.inpe.inpe.data.model.XRayLatestEvent
import com.inpe.inpe.data.repository.XRayRepository
import com.inpe.inpe.data.service.XRayLatestEventResult
import com.inpe.inpe.data.service.XrayResult
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class XRayFluxViewModel(private val dataSource: XRayRepository) : ViewModel() {
    val xRaysLiveData: MutableLiveData<List<XRay>> = MutableLiveData()
    val xRaysLatestEventData: MutableLiveData<List<XRayLatestEvent>> = MutableLiveData()
    private val viewFlipperLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()

    fun getXRays() {
        dataSource.getSevenDaysXRays {
            when (it) {
                is XrayResult.Success -> {
                    xRaysLiveData.value = it.xRays
                    viewFlipperLiveData.value = Pair(1, null)
                }
                is XrayResult.Error -> {
                    if (it.code == 401) {
                        viewFlipperLiveData.value = Pair(2, R.string.error_401)
                    } else {
                        viewFlipperLiveData.value = Pair(2, R.string.unavailable_server)
                    }
                }
                is XrayResult.ServerError -> {
                    viewFlipperLiveData.value = Pair(2, R.string.fatal_error)
                }
            }
        }
    }

    fun getXRaysLatestEvent() {
        dataSource.getLatestXRayEvent {
            when (it) {
                is XRayLatestEventResult.Success -> {
                    xRaysLatestEventData.value = it.xrays
                    viewFlipperLiveData.value = Pair(1, null)
                }
                is XRayLatestEventResult.Error -> {
                    if (it.code == 401) viewFlipperLiveData.value = Pair(2, R.string.error_401)
                    else viewFlipperLiveData.value = Pair(2, R.string.unavailable_server)
                }
                is XRayLatestEventResult.ServerError -> {
                    viewFlipperLiveData.value = Pair(2, R.string.fatal_error)
                }
            }
        }
    }

    fun getFormattedDate(dateTime: String) : String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)

        val localDate = LocalDateTime.parse(dateTime, inputFormatter)
        return  outputFormatter.format(localDate)
    }

    fun getFormattedText(moment: String, dateTime: String, classification: String, ratio: Float? = null): String {
        val time = getFormattedDate(dateTime)

        ratio?.let {
            val ratioFormatted = ratio.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)

            return "$moment \n$time\nClassificação $classification\nProporção $ratioFormatted"
        }

        return "$moment \n$time\nClassificação $classification"
    }

    fun getChartModel(xrays: List<XRay>) : AAChartModel {
        val x = xrays.filter { it.energy == GOES_16_LONG }.map { it ->
            if (it.flux > 0) {
                val a = it.flux.toString()

                val x = if (a.contains("-10")) a.removeRange(a.length - 4, a.length)
                else a.removeRange(a.length - 3, a.length)

                x.toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_EVEN).toDouble()
            } else {
                0.0
            }
        }

        val y = xrays.filter { it.energy == GOES_16_SHORT }.map {
            if (it.flux > 0) {
                val a = it.flux.toString()

                val v = if (a.contains("-10")) a.removeRange(a.length - 4, a.length)
                else a.removeRange(a.length - 3, a.length)

                v.toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_EVEN).toDouble()
            } else {
                0.0
            }
        }

        return AAChartModel()
            .chartType(AAChartType.Line)
            .yAxisGridLineWidth(0.0.toFloat())
            .dataLabelsEnabled(true)
            .legendEnabled(true)
            .xAxisVisible(true)
            .zoomType(AAChartZoomType.XY)
            .title("Fluxo de Raio-X GOES")

            .series(
                arrayOf(
                AASeriesElement()
                    .name("Goes-16 Long")
                    .data(x.toTypedArray()),
                AASeriesElement()
                    .name("Goes-16 Short")
                    .data(y.toTypedArray())
               )
            )
    }

    // TODO - Ver se somente Xrays 1 day serve.
    // se nao plotar, validar se com média de 5 em 5 minutos tirando média do flux, mostra.

    class ViewModelFactory(val dataSource: XRayRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(XRayFluxViewModel::class.java)) {
                return XRayFluxViewModel(dataSource) as T
            }

            throw IllegalArgumentException("Uknown ViewModel Class")
        }
    }

    companion object {
        const val GOES_16_LONG = "0.1-0.8nm"
        const val GOES_16_SHORT = "0.05-0.4nm"
    }
}