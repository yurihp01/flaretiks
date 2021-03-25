package com.inpe.inpe.data.repository

import com.inpe.inpe.data.model.XRay
import com.inpe.inpe.data.model.XRayLatestEvent
import com.inpe.inpe.data.service.ApiService
import com.inpe.inpe.data.service.XRayLatestEventResult
import com.inpe.inpe.data.service.XrayResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class XRayApiDataSource : XRayRepository {
    override fun getSevenDaysXRays(xRayResultCallback: (result: XrayResult) -> Unit) {
        ApiService.service.getSevenDaysXRays().enqueue(object : Callback<List<XRay>> {
            override fun onFailure(call: Call<List<XRay>>, t: Throwable) {
                xRayResultCallback(XrayResult.ServerError)
            }

            override fun onResponse(call: Call<List<XRay>>, response: Response<List<XRay>>) {
                when {
                    response.isSuccessful -> {
                        val xRays: MutableList<XRay> = mutableListOf()

                        response.body()?.let {
                            for (result in it) {
                                val xRay = XRay(result.timeTag, result.satellite, result.flux, result.energy)
                                xRays.add(xRay)
                            }
                        }

                        xRayResultCallback(XrayResult.Success(xRays))
                    }
                    else -> xRayResultCallback(XrayResult.Error(response.code()))
                }
            }

        })
    }

    override fun getLatestXRayEvent(xRayResultCallback: (result: XRayLatestEventResult) -> Unit) {
        ApiService.service.getLatestXrayEvent().enqueue(object : Callback<List<XRayLatestEvent>> {
            override fun onFailure(call: Call<List<XRayLatestEvent>>, t: Throwable) {
                xRayResultCallback(XRayLatestEventResult.ServerError)
            }

            override fun onResponse(call: Call<List<XRayLatestEvent>>, response: Response<List<XRayLatestEvent>>) {
                when {
                    response.isSuccessful -> {
                        val xRays: MutableList<XRayLatestEvent> = mutableListOf()

                        response.body()?.let {
                            for (result in it) {
                                val xRay = XRayLatestEvent(result.timeTag, result.currentClass, result.currentRatio,
                                    result.currentIntXrLong, result.beginTime, result.beginClass, result.maxTime, result.maxClass,
                                    result.endTime, result.endClass, result.maxRatioTime, result.maxRatio, result.satellite
                                )

                                xRays.add(xRay)
                            }
                        }

                        xRayResultCallback(XRayLatestEventResult.Success(xRays))
                    }
                    else -> xRayResultCallback(XRayLatestEventResult.Error(response.code()))
                }
            }

        })
    }
}