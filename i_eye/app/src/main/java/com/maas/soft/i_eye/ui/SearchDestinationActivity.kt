package com.maas.soft.i_eye.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_search_destination.*
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.maas.soft.i_eye.R


class SearchDestinationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_destination)
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(et_search_dest_search.windowToken, 0)

        setClickListener()
    }

    private fun setClickListener() {
        cl_top_dest_search.setOnClickListener {
            val intent = Intent(applicationContext, CheckDestinationActivity::class.java)
            startActivity(intent)
        }
    }
}
