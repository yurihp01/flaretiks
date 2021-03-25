package com.inpe.inpe.data.repository

import com.inpe.inpe.data.service.XRayLatestEventResult
import com.inpe.inpe.data.service.XrayResult

interface XRayRepository {
    fun getSevenDaysXRays(XRayResultCallback: (result : XrayResult) -> Unit)
    fun getLatestXRayEvent(XRayResultCallback: (result: XRayLatestEventResult) -> Unit)
}