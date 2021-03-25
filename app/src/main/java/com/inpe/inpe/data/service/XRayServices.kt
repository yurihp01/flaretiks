package com.inpe.inpe.data.service

import com.inpe.inpe.data.model.XRay
import com.inpe.inpe.data.model.XRayLatestEvent
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface XRayServices {
        @GET("xrays-1-day.json")
        fun getSevenDaysXRays() : Call<List<XRay>>

        @GET("xray-flares-latest.json")
        fun getLatestXrayEvent() : Call<List<XRayLatestEvent>>
}