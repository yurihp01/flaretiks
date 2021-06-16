package com.inpe.inpe.presentation.solarImages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.inpe.inpe.R
import com.inpe.inpe.data.repository.XRayApiDataSource

class SolarImagesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solar_images)

        val viewModel = SolarImagesViewModel.ViewModelFactory(XRayApiDataSource())
            .create(SolarImagesViewModel::class.java)

        viewModel.getSolarUrl()

        viewModel.html.observe(this, Observer {
            print(it)
        })
    }
}