package com.inpe.inpe.data.service

import com.inpe.inpe.data.model.XRay

sealed class XrayResult {
    class Success(val xRays: List<XRay>) : XrayResult()
    class Error(val code: Int) : XrayResult()
    object ServerError : XrayResult()
}