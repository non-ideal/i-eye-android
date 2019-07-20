package com.maas.soft.i_eye.ui.reserve_after

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.maas.soft.i_eye.R
import kotlinx.android.synthetic.main.activity_arrive_at_stop.*

class ArriveAtStopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arrive_at_stop)
        changeStatusBarColor()
        setListener()
        //TODO 지금이 되면 tv_guide_arrive_at_stop 내용 바껴야함
    }

    private fun setListener() {
        btn_help_arrive_at_stop.setOnClickListener {
            startActivity(Intent(this, RequestToHelpActivity::class.java))
            finish()
        }
    }

    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.black, theme)
        } else {
            window.statusBarColor = resources.getColor(R.color.black)
        }
    }
}
