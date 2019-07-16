package com.maas.soft.i_eye.ui

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_destination_voice_input.*
import android.widget.Toast
import android.speech.RecognitionListener
import com.maas.soft.i_eye.R
import android.Manifest.permission.RECORD_AUDIO
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat




class DestinationVoiceInputActivity : AppCompatActivity() {
    val i : Intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    lateinit var mRecognizer : SpeechRecognizer
    var dest : String = ""

    private val listener = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle) {
            println("onReadyForSpeech.........................")
        }

        override fun onBeginningOfSpeech() {
            Toast.makeText(applicationContext, "지금부터 말을 해주세요!", Toast.LENGTH_SHORT).show()
        }

        override fun onRmsChanged(rmsdB: Float) {
            println("onRmsChanged.........................")
        }

        override fun onBufferReceived(buffer: ByteArray) {
            println("onBufferReceived.........................")
        }

        override fun onEndOfSpeech() {
            println("onEndOfSpeech.........................")
        }

        override fun onError(error: Int) {
            Toast.makeText(applicationContext, "천천히 다시 말해주세요.", Toast.LENGTH_SHORT).show()
        }

        override fun onPartialResults(partialResults: Bundle) {
            println("onPartialResults.........................")
        }

        override fun onEvent(eventType: Int, params: Bundle) {
            println("onEvent.........................")
        }

        override fun onResults(results: Bundle) {
            var key = ""
            key = SpeechRecognizer.RESULTS_RECOGNITION
            val mResult = results.getStringArrayList(key)
            val rs = arrayOfNulls<String>(mResult!!.size)
            mResult.toArray(rs)
            dest = rs[0] ?: "인식 실패"
            Toast.makeText(applicationContext, dest, Toast.LENGTH_SHORT).show()
            mRecognizer.startListening(i)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination_voice_input)
        changeStatusBarColor()
        setBtnListener()
        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.packageName)
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        mRecognizer.setRecognitionListener(listener)
    }

    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.black, theme)
        } else {
            window.statusBarColor = resources.getColor(R.color.black)
        }
    }
    
    private fun setBtnListener() {
        btn_voice_dest_input.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (ContextCompat.checkSelfPermission(applicationContext, RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, arrayOf(RECORD_AUDIO), 1)
                        //권한을 허용하지 않는 경우
                    } else {
                        //권한을 허용한 경우
                        try {
                            mRecognizer.startListening(i)
                        } catch (e: SecurityException) {
                            e.printStackTrace()
                        }
                    }

                    true
                }
                MotionEvent.ACTION_UP -> {
                    var mIntent = Intent(this, CheckDestinationVoiceInputActivity::class.java)
                    mIntent.putExtra("VOICE_DEST", dest)
                    startActivity(mIntent)
                    true
                }
                else -> true
            }
        }
    }
}
