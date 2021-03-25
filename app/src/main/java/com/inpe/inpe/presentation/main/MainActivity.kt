package com.inpe.inpe.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inpe.inpe.R
import com.inpe.inpe.presentation.magneticsFields.MagneticsFieldsActivity
import com.inpe.inpe.presentation.solarImages.SolarImagesActivity
import com.inpe.inpe.presentation.xrayFlux.XRayFluxActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_solar_image.setOnClickListener {
            val intent = Intent(this, SolarImagesActivity::class.java)
            startActivity(intent)
        }

        btn_xray_flux.setOnClickListener {
            val intent = Intent(this, XRayFluxActivity::class.java)
            startActivity(intent)
        }

        btn_magnetic_fields.setOnClickListener {
            val intent = Intent(this, MagneticsFieldsActivity::class.java)
            startActivity(intent)
        }
    }
}