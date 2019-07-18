package com.maas.soft.i_eye.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.maas.soft.i_eye.R
import com.maas.soft.i_eye.model.PathResDto
import com.maas.soft.i_eye.network.ApplicationController
import com.maas.soft.i_eye.network.NetworkService
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.activity_directions.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DirectionsActivity : AppCompatActivity() {

    private val REQUEST_CODE_LOCATION : Int = 2
    private var latitude : Double? = null
    private var longitude : Double? = null

    private var networkService: NetworkService = ApplicationController.instance.networkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directions)
        getPathResponse()
        changeStatusBarColor()
        getLatLng()

        val tMapView = TMapView(this)
        tMapView.setSKTMapApiKey("767dc065-35e7-4782-a787-202f73d8d976")
        Log.d("@@@", "$latitude, $longitude")
        tMapView.setLocationPoint(longitude!!,latitude!!)
        tMapView.setCenterPoint(longitude!!,latitude!!)
        tMapView.setCompassMode(false)
        tMapView.setIconVisibility(true)
        tMapView.zoomLevel = 17
        tMapView.mapType = TMapView.MAPTYPE_HYBRID  //일반지도
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN)
        tMapView.setTrackingMode(false)
        tMapView.setSightVisible(false)
        tMapView.contentDescription = "지도 영역입니다"
        mapview_directions.addView(tMapView)
    }

    private fun getLatLng() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var currentLatLng : Location? = null

        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), this.REQUEST_CODE_LOCATION)
            getLatLng()
        } else {
            val locationProvider = LocationManager.GPS_PROVIDER
            currentLatLng = locationManager?.getLastKnownLocation(locationProvider)
        }
        currentLatLng?.let {
            latitude = currentLatLng.latitude
            longitude = currentLatLng.longitude
        }
    }

    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.black, theme)
        }
        else {
            window.statusBarColor = resources.getColor(R.color.black)
        }
    }

    private fun getPathResponse() {
        var jsonObject = JSONObject()
        jsonObject.put("endX", 127.09404734529575)
        jsonObject.put("endY", 37.50612432766213)
        jsonObject.put("startX", 127.08370508148472)
        jsonObject.put("startY", 37.52946809068537)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val getPathResponse = networkService.getPathResponse(gsonObject)

        getPathResponse!!.enqueue(object : Callback<List<PathResDto>> {
            override fun onFailure(call: Call<List<PathResDto>>, t: Throwable) {
                Log.d("Log::LoginActivity", t.message)
                Log.d("Log::LoginActivity","onFailure")

            }
            override fun onResponse(call: Call<List<PathResDto>>, response: Response<List<PathResDto>>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            Log.d("Log::LoginActivity","200")
                            Log.d("Log::LoginActivity", response.body().toString())
                        }
                        403 -> {
                            Log.d("Log::LoginActivity","403")

                        }
                        500 -> {
                            Log.d("Log::LoginActivity","500")

                        }
                        else -> {
                            Log.d("Log::LoginActivity","else")
                        }
                    }
                }
            }

        })
    }
}
