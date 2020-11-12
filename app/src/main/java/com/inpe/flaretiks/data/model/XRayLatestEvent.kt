package com.inpe.flaretiks.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class XRayLatestEvent (
    @Json(name = "time_tag") val timeTag: String,
    @Json(name = "current_class") val currentClass: String,
    @Json(name = "current_ratio") val currentRatio: Float,
    @Json(name = "current_int_xrlong") val currentIntXrLong: Float,
    @Json(name = "begin_time") val beginTime: String,
    @Json(name = "begin_class") val beginClass: String,
    @Json(name = "max_time") val maxTime: String,
    @Json(name = "max_class") val maxClass: String,
    @Json(name = "end_time") val endTime: String,
    @Json(name = "end_class") val endClass: String,
    @Json(name = "max_ratio_time") val maxRatioTime: String,
    @Json(name = "max_ratio") val maxRatio: String,
    val satellite: Int
)