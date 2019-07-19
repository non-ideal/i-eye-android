package com.maas.soft.i_eye.ui

import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_reserve.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReserveActivity : AppCompatActivity() {

    private var latitude : Double? = null
    private var longitude : Double? = null
    private var desLatitude : Double? = null
    private var desLongitude : Double? = null

    private var networkService: NetworkService = ApplicationController.instance.networkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve)
        getLatLng()
        getPathResponse()
        setClickListener()
    }

    private fun getLatLng() {
        latitude = SharedPreferenceController.getStartLat(this)
        longitude = SharedPreferenceController.getStartLng(this)
        desLatitude = SharedPreferenceController.getDestinationLat(this)
        desLongitude = SharedPreferenceController.getDestinationLng(this)

        Log.d("길찾기 좌표 확인", "현재 ($longitude, $latitude) / 목적지 ($desLongitude, $desLatitude)")
    }

    private fun getBusNumber(pathResDtoList : List<PathResDto>) {
        for(i in pathResDtoList) {
            i.busNumber?.let {
                tv_bus_number_reserve.text = it
                SharedPreferenceController.setBusNumber(this, it)
            }
        }
    }

    private fun setClickListener() {
        btn_yes_reserve.setOnClickListener {
            SharedPreferenceController.setStatus(this, 1)
            val intent = Intent(applicationContext, ReservedMainActivity::class.java)
            startActivity(intent)
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
                            getBusNumber(response.body()!!)
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
