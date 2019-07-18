package com.maas.soft.i_eye.ui

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.maas.soft.i_eye.R
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.provider.Settings
import com.maas.soft.i_eye.controller.SharedPreferenceController
import com.maas.soft.i_eye.ui.tutorial.Tutorial1Activity
import java.util.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        SharedPreferenceController.clearAll(this)
        checkFirstRun()
        changeStatusBarColor()
    }

    private fun checkFirstRun() {
        var firstRun = SharedPreferenceController.getFirstRun(this)
        lateinit var intent : Intent
        if(firstRun) {
            var ANDROID_ID : String = Settings.Secure.getString(applicationContext.contentResolver, Settings.Secure.ANDROID_ID)
            SharedPreferenceController.setAndroidID(this, ANDROID_ID)
            //TODO 서버로 ANDROID_ID 전송
            SharedPreferenceController.setFirstRun(this, false)
            //TODO 예약 상태에 따라 화면 이동
            intent = Intent(applicationContext, NoReservedMainActivity::class.java)
        }
        else {
            intent = Intent(applicationContext, Tutorial1Activity::class.java)
        }
        moveToNextActivity(intent)
    }

    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.black, theme)
        } else {
            window.statusBarColor = resources.getColor(R.color.black)
        }
    }

    private fun moveToNextActivity(intent : Intent) {
        val handler = Handler()
        handler.postDelayed({
            startActivity(intent)
            finish()
        }, 1500)
    }
}
