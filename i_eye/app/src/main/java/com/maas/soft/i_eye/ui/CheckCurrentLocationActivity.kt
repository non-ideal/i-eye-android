package com.maas.soft.i_eye.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_check_current_location.*

class CheckCurrentLocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(com.maas.soft.i_eye.R.layout.activity_check_current_location)
        setClickListener()
        setCurrentLocationText()
    }

    private fun setCurrentLocationText() {
        var currentLocation : String = intent.getStringExtra("currentLocation")
        tv_current_location.text = currentLocation
    }

    private fun setClickListener() {
        btn_yes_check_current_location.setOnClickListener {
            //TODO 서버로 현재 위치 위도, 경도 보내기
            var latitude : Double = intent.getDoubleExtra("latitude", 0.0)
            var longitude : Double = intent.getDoubleExtra("longitude", 0.0)

            val intent = Intent(applicationContext, SearchDestinationActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_no_check_current_location.setOnClickListener {
            val intent = Intent(applicationContext, SearchingForLocationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}
