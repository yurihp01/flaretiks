package com.inpe.inpe.data.service

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiService {
    private fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://services.swpc.noaa.gov/json/goes/primary/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val service: XRayServices = initRetrofit().create(XRayServices::class.java)
}