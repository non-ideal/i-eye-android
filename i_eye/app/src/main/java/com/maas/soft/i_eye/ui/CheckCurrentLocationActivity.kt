package com.maas.soft.i_eye.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.maas.soft.i_eye.R
import kotlinx.android.synthetic.main.activity_check_current_location.*

class CheckCurrentLocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_current_location)
        setClickListener()
    }

    private fun setClickListener() {
        btn_yes_check_current_location.setOnClickListener {
            val intent = Intent(applicationContext, SearchDestinationActivity::class.java)
            startActivity(intent)
        }

        btn_no_check_current_location.setOnClickListener {
            val intent = Intent(applicationContext, SearchingForLocationActivity::class.java)
            startActivity(intent)
        }
    }
}
