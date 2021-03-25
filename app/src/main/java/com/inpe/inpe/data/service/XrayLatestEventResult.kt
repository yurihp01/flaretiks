package com.inpe.inpe.data.service

import com.inpe.inpe.data.model.XRayLatestEvent

sealed class XRayLatestEventResult {
    class Success(val xrays: List<XRayLatestEvent>) : XRayLatestEventResult()
    class Error(val code: Int) : XRayLatestEventResult()
    object ServerError : XRayLatestEventResult()
}