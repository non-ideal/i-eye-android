package com.maas.soft.i_eye.ui

import android.Manifest
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
import android.location.LocationManager
import android.content.pm.PackageManager
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.support.v4.app.ActivityCompat
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.speech.tts.TextToSpeech
import com.maas.soft.i_eye.model.Type
import java.util.*
import kotlin.collections.ArrayList


class DirectionsActivity : AppCompatActivity() {
    private lateinit var tts : TextToSpeech
    private lateinit var tMapView : TMapView
    private lateinit var locationManager : LocationManager
    private var status = 1

    private var latitude : Double = 0.0
    private var longitude : Double = 0.0
    private var desLatitude : Double = 0.0
    private var desLongitude : Double = 0.0

    private var paths : ArrayList<PathResDto> = ArrayList()

    private var networkService: NetworkService = ApplicationController.instance.networkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directions)

        tts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
            if(status!=TextToSpeech.ERROR)
                tts.language = Locale.KOREAN
        })
        locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        status = SharedPreferenceController.getStatus(this)

        getLatLng()
        getTMap()
        getPathResponse()
        changeStatusBarColor()
        setLocationListener()
    }

    private fun setLocationListener() {
        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location?) {
                if (location != null) {
                    latitude = location.latitude
                    longitude = location.longitude
                }

                if(desLatitude-0.0002 <= latitude && latitude <= desLatitude+0.0002 && desLongitude-0.0002 <= longitude && longitude <= desLongitude+0.0002){
                    if(status==1) {
                        tts.speak("버스 정류장에 도착하였습니다.", TextToSpeech.QUEUE_FLUSH, null, this.hashCode().toString())
                        SharedPreferenceController.setStatus(applicationContext, 2)
                        Intent(applicationContext, ArriveAtStopActivity::class.java).let {
                            it.putExtra("BUS_NUM", 1125)
                            // TODO 버스 정보 등 버스 예약에 필요한 정보 넘기기
                            startActivity(it)
                            finish()
                        }
                    }else {
                        tts.speak("목적지에 도착하였습니다. 하단의 안내 종료 버튼을 눌러서 안내를 종료하세요.", TextToSpeech.QUEUE_FLUSH, null, this.hashCode().toString())
                        SharedPreferenceController.setStatus(applicationContext, 0)
                        startActivity(Intent(applicationContext, NoReservedMainActivity::class.java))
                        finish()
                    }
                }

                chkPoint()
            }


            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

            override fun onProviderEnabled(provider: String?) {
            }

            override fun onProviderDisabled(provider: String?) {
            }
        }

        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0f, locationListener)
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0f, locationListener)
    }

    private fun chkPoint() {
        // TODO 현재 위치가 어떤 POINT 근방(경도 위도 +-0.0002)이라면 description 안내 메세지 TTS
        // TODO 다음 경로 안내 TTS (ex. 이어서 직진 300m입니다.)
    }

    private fun getLatLng() {
        latitude = SharedPreferenceController.getStartLat(this)
        longitude = SharedPreferenceController.getStartLng(this)
        desLatitude = SharedPreferenceController.getDestinationLat(this)
        desLongitude = SharedPreferenceController.getDestinationLng(this)

        Log.d("길찾기 좌표 확인", "현재 ($longitude, $latitude) / 목적지 ($desLongitude, $desLatitude)")
    }

    private fun getTMap() {
        tMapView = TMapView(this, 5)
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
        paths = pathResDtoList as ArrayList<PathResDto>

        if (status==1) {
            // 예약O, 탑승 전
            // 출발지부터 버스 정거장까지 draw
            for(i in pathResDtoList) {
                alTMapPoint.add(TMapPoint(i.y, i.x))

                if(i.type == Type.BUS_STOP)
                    break
            }

        } else {
            // 하차 후
            // 내린 정거장 부터 목적지까지 draw
            var flag = false
            for(i in pathResDtoList) {
                if (flag){
                    alTMapPoint.add(TMapPoint(i.y, i.x))
                }
                if (i.type == Type.BUS_STOP)
                    flag = true
            }

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
