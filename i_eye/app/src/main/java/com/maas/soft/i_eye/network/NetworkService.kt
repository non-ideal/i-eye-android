package com.maas.soft.i_eye.network

import com.google.gson.JsonObject
import com.maas.soft.i_eye.model.BusResDto
import com.maas.soft.i_eye.model.PathResDto
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {

    // path-controller
    @Headers("Content-Type: application/json")
    @POST("api/path")
    fun getPathResponse(
        @Body body: JsonObject
    ): Call<PathResDto>

    // bus-controller
    @Headers("Content-Type: application/json")
    @POST("api/bus")
    fun getArriveBusTime(
        @Body body: JsonObject
    ): Call<BusResDto>
}