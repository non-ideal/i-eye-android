package com.maas.soft.i_eye.ui.reserve_after

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.maas.soft.i_eye.R
import com.maas.soft.i_eye.controller.SharedPreferenceController
import com.maas.soft.i_eye.ui.reserve_before.NoReservedMainActivity
import kotlinx.android.synthetic.main.activity_reserved_main.*

class ReservedMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserved_main)
        setClickListener()
        tv_bus_number_reserved_main.text = SharedPreferenceController.getBusNumber(this)
    }

    private fun setClickListener() {
        btn_cancel_reserved_main.setOnClickListener {
            val intent = Intent(applicationContext, NoReservedMainActivity::class.java)
            startActivity(intent)
        }
        btn_directions_reserved_main.setOnClickListener {
            val intent = Intent(applicationContext, DirectionsActivity::class.java)
            startActivity(intent)
        }
    }
}
