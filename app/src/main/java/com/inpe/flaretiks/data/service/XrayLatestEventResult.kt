package com.inpe.flaretiks.data.service

import com.inpe.flaretiks.data.model.XRayLatestEvent

sealed class XRayLatestEventResult {
    class Success(val xrays: List<XRayLatestEvent>) : XRayLatestEventResult()
    class Error(val code: Int) : XRayLatestEventResult()
    object ServerError : XRayLatestEventResult()
}