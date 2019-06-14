package com.maas.soft.i_eye.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.maas.soft.i_eye.R
import kotlinx.android.synthetic.main.activity_check_destination.*

class CheckDestinationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_destination)
        setClickListener()
    }

    private fun setClickListener() {
        btn_yes_check_destination.setOnClickListener {
            val intent = Intent(applicationContext, ReservedMainActivity::class.java)
            startActivity(intent)
        }

        btn_no_check_destination.setOnClickListener {
            val intent = Intent(applicationContext, SearchDestinationActivity::class.java)
            startActivity(intent)
        }
    }
}
