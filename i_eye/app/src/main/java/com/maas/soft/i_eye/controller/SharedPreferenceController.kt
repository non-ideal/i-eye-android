package com.maas.soft.i_eye.controller

import android.content.Context
import android.content.SharedPreferences
import android.content.Context.MODE_PRIVATE



object SharedPreferenceController {

    private val FIRST_RUN : String = "first_run"
    private val ANDROID_ID : String = "android_id"
    private val STATUS : Int = 0
    private val START_LAT : String = "start_lat"
    private val START_LNG : String = "start_lng"
    private val DESTINATION_LAT : String = "destination_lat"
    private val DESTINATION_LNG : String = "destination_lng"
    private val BUS_NUMBER : String = "bus_number"
    private val CLEAR_ALL : String = "clear_all"

    /**
     * FIRST_RUN
     * Boolean
     */
    fun setFirstRun(ctx : Context, input_first_run : Boolean) {
        val preferences : SharedPreferences = ctx.getSharedPreferences(FIRST_RUN, MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putBoolean(FIRST_RUN, input_first_run)
        editor.commit()
    }

    fun getFirstRun(ctx : Context) : Boolean {
        val preferences : SharedPreferences = ctx.getSharedPreferences(FIRST_RUN, MODE_PRIVATE)
        return preferences.getBoolean(FIRST_RUN, true)   // (key 명, 든 게 없을 때 리턴할 값)
    }


    /**
     * ANDROID_ID
     * String
     */
    fun setAndroidID(ctx : Context, input_android_id : String) {
        val preferences : SharedPreferences = ctx.getSharedPreferences(ANDROID_ID, MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putString(ANDROID_ID, input_android_id)
        editor.commit()
    }

    fun getAndroidID(ctx : Context) : String {
        val preferences : SharedPreferences = ctx.getSharedPreferences(ANDROID_ID, MODE_PRIVATE)
        return preferences.getString(ANDROID_ID, "")
    }

    /**
     * START_LAT
     * Double
     */
    fun setStartLat(ctx : Context, input_start_lat : Double) {
        val preferences : SharedPreferences = ctx.getSharedPreferences(START_LAT, MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putFloat(START_LAT, input_start_lat.toFloat())
        editor.commit()
    }

    fun getStartLat(ctx : Context) : Double {
        val preferences : SharedPreferences = ctx.getSharedPreferences(START_LAT, MODE_PRIVATE)
        return preferences.getFloat(START_LAT, 0.0F).toDouble()
    }

    /**
     * START_LNG
     * Double
     */
    fun setStartLng(ctx : Context, input_start_lng : Double) {
        val preferences : SharedPreferences = ctx.getSharedPreferences(START_LNG, MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putFloat(START_LNG, input_start_lng.toFloat())
        editor.commit()
    }

    fun getStartLng(ctx : Context) : Double {
        val preferences : SharedPreferences = ctx.getSharedPreferences(START_LNG, MODE_PRIVATE)
        return preferences.getFloat(START_LNG, 0.0F).toDouble()
    }

    /**
     * DESTINATION_LAT
     * Double
     */
    fun setDestinationLat(ctx : Context, input_des_lat : Double) {
        val preferences : SharedPreferences = ctx.getSharedPreferences(DESTINATION_LAT, MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putFloat(DESTINATION_LAT, input_des_lat.toFloat())
        editor.commit()
    }

    fun getDestinationLat(ctx : Context) : Double {
        val preferences : SharedPreferences = ctx.getSharedPreferences(DESTINATION_LAT, MODE_PRIVATE)
        return preferences.getFloat(DESTINATION_LAT, 0.0F).toDouble()
    }

    /**
     * DESTINATION_LNG
     * Double
     */
    fun setDestinationLng(ctx : Context, input_des_lng : Double) {
        val preferences : SharedPreferences = ctx.getSharedPreferences(DESTINATION_LNG, MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putFloat(DESTINATION_LNG, input_des_lng.toFloat())
        editor.commit()
    }

    fun getDestinationLng(ctx : Context) : Double {
        val preferences : SharedPreferences = ctx.getSharedPreferences(DESTINATION_LNG, MODE_PRIVATE)
        return preferences.getFloat(DESTINATION_LNG, 0.0F).toDouble()
    }

    /**
     * BUS_NUMBER
     * String
     */
    fun setBusNumber(ctx : Context, input_bus_num : String) {
        val preferences : SharedPreferences = ctx.getSharedPreferences(BUS_NUMBER, MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putString(BUS_NUMBER, input_bus_num)
        editor.commit()
    }

    fun getBusNumber(ctx : Context) : String {
        val preferences : SharedPreferences = ctx.getSharedPreferences(BUS_NUMBER, MODE_PRIVATE)
        return preferences.getString(BUS_NUMBER, "")
    }

    /**
     * CLEAR_ALL
     */
    fun clearAll(ctx : Context) {
        val preferences : SharedPreferences = ctx.getSharedPreferences(CLEAR_ALL, MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferences.edit()
        editor.clear()
        editor.commit()
    }

}