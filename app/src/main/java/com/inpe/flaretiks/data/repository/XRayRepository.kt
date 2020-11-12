package com.inpe.flaretiks.data.repository

import com.inpe.flaretiks.data.service.XRayLatestEventResult
import com.inpe.flaretiks.data.service.XrayResult

interface XRayRepository {
    fun getSevenDaysXRays(XRayResultCallback: (result : XrayResult) -> Unit)
    fun getLatestXRayEvent(XRayResultCallback: (result: XRayLatestEventResult) -> Unit)
}