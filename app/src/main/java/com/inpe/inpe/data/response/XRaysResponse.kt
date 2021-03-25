package com.inpe.inpe.data.response

import com.inpe.inpe.data.model.XRay
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class XRaysResponse(
    val xrays: List<XRay>
)