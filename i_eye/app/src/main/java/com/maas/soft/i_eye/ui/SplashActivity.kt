package com.maas.soft.i_eye.ui

import android.Manifest
import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.maas.soft.i_eye.R
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import com.maas.soft.i_eye.controller.SharedPreferenceController
import com.maas.soft.i_eye.ui.tutorial.Tutorial1Activity
import android.telephony.TelephonyManager
import android.util.Log


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
        if(firstRun) { //첫 실행
            while (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), 100)
            }
            var phoneNum: String
            val telManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            phoneNum = telManager.line1Number
            if (phoneNum.startsWith("+82")) {
                phoneNum = phoneNum.replace("+82", "0")
            }
            Log.d("SplashActivity", "폰 번호: $phoneNum")
            SharedPreferenceController.setPhoneNum(this, phoneNum)

            //TODO 서버로 휴대폰 번호 전송
            SharedPreferenceController.setFirstRun(this, false)
            //TODO 예약 상태에 따라 화면 이동
            intent = Intent(applicationContext, Tutorial1Activity::class.java)
        }
        else {
            intent = Intent(applicationContext, NoReservedMainActivity::class.java)
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
