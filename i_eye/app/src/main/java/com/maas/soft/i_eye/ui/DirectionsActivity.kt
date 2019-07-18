package com.maas.soft.i_eye.ui

import android.graphics.Color
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
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapPolyLine


class DirectionsActivity : AppCompatActivity() {

    lateinit var tMapView : TMapView

    private var latitude : Double? = null
    private var longitude : Double? = null
    private var desLatitude : Double? = null
    private var desLongitude : Double? = null

    private var networkService: NetworkService = ApplicationController.instance.networkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directions)
        getLatLng()
        getTMap()
        getPathResponse()
        changeStatusBarColor()
    }

    private fun getLatLng() {
        latitude = SharedPreferenceController.getStartLat(this)
        longitude = SharedPreferenceController.getStartLng(this)
        desLatitude = SharedPreferenceController.getDestinationLat(this)
        desLongitude = SharedPreferenceController.getDestinationLng(this)

        Log.d("길찾기 좌표 확인", "현재 ($longitude, $latitude) / 목적지 ($desLongitude, $desLatitude)")
    }

    private fun getTMap() {
        tMapView = TMapView(this)
        tMapView.setSKTMapApiKey("767dc065-35e7-4782-a787-202f73d8d976")
        tMapView.setLocationPoint(longitude!!,latitude!!)
        tMapView.setCenterPoint(longitude!!,latitude!!)
        tMapView.setCompassMode(true)
        tMapView.setIconVisibility(true)
        tMapView.zoomLevel = 30
        tMapView.mapType = TMapView.MAPTYPE_HYBRID  //일반지도
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN)
        tMapView.setTrackingMode(true)
        tMapView.setSightVisible(true)
        tMapView.contentDescription = "지도 영역입니다"
        mapview_directions.addView(tMapView)
    }

    private fun drawLine(pathResDtoList : List<PathResDto>) {
        val alTMapPoint = ArrayList<TMapPoint>()

        for(i in pathResDtoList) {
            alTMapPoint.add(TMapPoint(i.y, i.x))
        }

        val tMapPolyLine = TMapPolyLine()
        tMapPolyLine.lineColor = Color.parseColor("#FF6C00")
        tMapPolyLine.lineWidth = 80f
        for (i in 0 until alTMapPoint.size) {
            tMapPolyLine.addLinePoint(alTMapPoint[i])
        }
        tMapView.addTMapPolyLine("Line1", tMapPolyLine)
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
                Log.d("pathResponse 호출: ","onFailure")
                Log.d("pathResponse 에러: ", t.message)
            }
            override fun onResponse(call: Call<List<PathResDto>>, response: Response<List<PathResDto>>) {
                response?.let {
                    when (it.code()) {
                        200 -> {
                            Log.d("pathResponse 상태 코드: ","200")
                            Log.d("pathResponse 결과: ", response.body().toString())

                            drawLine(response.body()!!)
                        }
                        403 -> {
                            Log.d("pathResponse 상태 코드: ","403")

                        }
                        500 -> {
                            Log.d("pathResponse 상태 코드: ","500")

                        }
                        else -> {
                            Log.d("pathResponse 상태 코드: ", it.code().toString())
                        }
                    }
                }
            }

        })
    }
}
