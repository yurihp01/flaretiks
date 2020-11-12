package com.inpe.flaretiks.presentation.xrayFlux

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.inpe.flaretiks.R
import com.inpe.flaretiks.data.model.XRay
import com.inpe.flaretiks.data.model.XRayLatestEvent
import com.inpe.flaretiks.data.repository.XRayRepository
import com.inpe.flaretiks.data.service.XRayLatestEventResult
import com.inpe.flaretiks.data.service.XrayResult
import java.lang.IllegalArgumentException
import java.math.RoundingMode
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class XRayFluxViewModel(val dataSource: XRayRepository) : ViewModel() {
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


    class ViewModelFactory(val dataSource: XRayRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(XRayFluxViewModel::class.java)) {
                return XRayFluxViewModel(dataSource) as T
            }

            throw IllegalArgumentException("Uknown ViewModel Class")
        }
    }
}