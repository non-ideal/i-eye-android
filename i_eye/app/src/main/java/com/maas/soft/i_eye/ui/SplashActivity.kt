package com.maas.soft.i_eye.ui

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.maas.soft.i_eye.R
import android.content.Intent


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        changeStatusBarColor()
        moveToNextActivity()
    }

    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.black, theme)
        } else {
            window.statusBarColor = resources.getColor(R.color.black)
        }
    }

    private fun moveToNextActivity() {
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(applicationContext, NoReservedMainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)
    }
}
