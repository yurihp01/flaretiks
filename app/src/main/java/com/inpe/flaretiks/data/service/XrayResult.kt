package com.inpe.flaretiks.data.service

import com.inpe.flaretiks.data.model.XRay

sealed class XrayResult {
    class Success(val xRays: List<XRay>) : XrayResult()
    class Error(val code: Int) : XrayResult()
    object ServerError : XrayResult()
}