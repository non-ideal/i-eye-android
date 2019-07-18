package com.maas.soft.i_eye.ui

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.maas.soft.i_eye.R
import com.maas.soft.i_eye.controller.SharedPreferenceController
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

    private var latitude : Double? = null
    private var longitude : Double? = null
    private var desLatitude : Double? = null
    private var desLongitude : Double? = null

    private var networkService: NetworkService = ApplicationController.instance.networkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directions)
        getLatLng()
        getPathResponse()
        changeStatusBarColor()

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
        latitude = SharedPreferenceController.getStartLat(this)
        longitude = SharedPreferenceController.getStartLng(this)
        desLatitude = SharedPreferenceController.getDestinationLat(this)
        desLongitude = SharedPreferenceController.getDestinationLng(this)
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
        jsonObject.put("endX", desLongitude)
        jsonObject.put("endY", desLatitude)
        jsonObject.put("startX", longitude)
        jsonObject.put("startY", latitude)

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
