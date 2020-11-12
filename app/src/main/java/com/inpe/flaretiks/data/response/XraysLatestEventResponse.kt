package com.inpe.flaretiks.data.response

import com.inpe.flaretiks.data.model.XRayLatestEvent
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class XraysLatestEventResponse(
    val xraysList: List<XRayLatestEvent>
)