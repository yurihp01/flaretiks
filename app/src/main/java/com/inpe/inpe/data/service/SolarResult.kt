package com.inpe.inpe.data.service

sealed class SolarResult {
    class Success(val urls: Array<String>) : SolarResult()
    class Error(val code: Int) : SolarResult()
    object ServerError : SolarResult()
}