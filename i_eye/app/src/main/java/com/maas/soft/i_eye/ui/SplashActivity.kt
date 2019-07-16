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
import java.util.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        checkFirstRun()
        changeStatusBarColor()
        moveToNextActivity()

    }

    private fun checkFirstRun() {
        var mPref : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        var firstRun = mPref.getBoolean("firstRun", true)
        if(firstRun) {
            var ANDROID_ID : String = Settings.Secure.getString(applicationContext.contentResolver, Settings.Secure.ANDROID_ID)
            //TODO 서버로 ANDROID_ID 전송
            var editor : SharedPreferences.Editor = mPref.edit()
            editor.putBoolean("firstRun", false)
            editor.commit()
        }
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
