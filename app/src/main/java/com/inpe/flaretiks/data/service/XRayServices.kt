package com.inpe.flaretiks.data.service

import com.inpe.flaretiks.data.model.XRay
import com.inpe.flaretiks.data.model.XRayLatestEvent
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface XRayServices {
        @GET("xrays-7-day.json")
        fun getSevenDaysXRays() : Call<List<XRay>>

        @GET("xray-flares-latest.json")
        fun getLatestXrayEvent() : Call<List<XRayLatestEvent>>
}