package com.inpe.flaretiks.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class XRay (
    @Json(name = "time_tag") val timeTag: String,
    val satellite: Int,
    val flux: Double,
    val energy: String
)