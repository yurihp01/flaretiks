package com.inpe.inpe.data.response

import com.inpe.inpe.data.model.XRayLatestEvent
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class XraysLatestEventResponse(
    val xraysList: List<XRayLatestEvent>
)