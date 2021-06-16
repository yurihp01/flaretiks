package com.inpe.inpe.presentation.solarImages

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.inpe.inpe.R
import com.inpe.inpe.data.repository.XRayRepository
import com.inpe.inpe.data.service.SolarResult
import java.lang.IllegalArgumentException

class SolarImagesViewModel(private val dataSource: XRayRepository ) : ViewModel() {
    val html: MutableLiveData<String> = MutableLiveData()
    private val viewFlipperLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()

    fun getSolarUrl() {
        dataSource.getUrl {
            when (it) {
                is SolarResult.Success -> {
                    // utilizar urls para exibir imagens
                    // precisará configurar as urls, pois nao vem o link
                    // html.value = it.urls
                    viewFlipperLiveData.value = Pair(1, null)
                }
                is SolarResult.Error -> {
                    if (it.code == 401) {
                        viewFlipperLiveData.value = Pair(2, R.string.error_401)
                    } else {
                        viewFlipperLiveData.value = Pair(2, R.string.unavailable_server)
                    }
                }
                is SolarResult.ServerError -> {
                    viewFlipperLiveData.value = Pair(2, R.string.fatal_error)
                }
            }
        }
    }

    class ViewModelFactory(private val dataSource: XRayRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SolarImagesViewModel::class.java)) {
                return SolarImagesViewModel(dataSource) as T
            }

            throw IllegalArgumentException("Uknown ViewModel Class")
        }
    }
}