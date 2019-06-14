package com.maas.soft.i_eye.ui

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.maas.soft.i_eye.R
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.activity_directions.*


class DirectionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directions)
        changeStatusBarColor()

        val tMapView = TMapView(this)
        tMapView.setSKTMapApiKey("767dc065-35e7-4782-a787-202f73d8d976")
        tMapView.setLocationPoint(126.970325,37.556152)
        tMapView.setCenterPoint(126.970325,37.556152)
        tMapView.setCompassMode(false)
        tMapView.setIconVisibility(true)
        tMapView.zoomLevel = 15
        tMapView.mapType = TMapView.MAPTYPE_STANDARD  //일반지도
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN)
        tMapView.setTrackingMode(false)
        tMapView.setSightVisible(false)
        mapview_directions.addView(tMapView)
    }

    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.black, theme)
        }
        else {
            window.statusBarColor = resources.getColor(R.color.black)
        }
    }
}
