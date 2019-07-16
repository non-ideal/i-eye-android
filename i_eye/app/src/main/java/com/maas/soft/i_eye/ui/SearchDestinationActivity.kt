package com.maas.soft.i_eye.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_search_destination.*
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.maas.soft.i_eye.R
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem


class SearchDestinationActivity : AppCompatActivity() , View.OnClickListener {
    private val placeItems = ArrayList<String>()
    private lateinit var placeAdapter: PlaceAdapter
    private val tmapdata = TMapData()
    var item : ArrayList<TMapPOIItem> = ArrayList()

    override fun onClick(v: View?) {
        val intent = Intent(this, CheckDestinationActivity::class.java)
        intent.putExtra("Destination", placeItems[rv_search_dest_search.getChildAdapterPosition(v!!)])

        // TODO save destination coord
        // item[rv_search_dest_search.getChildAdapterPosition(v)].poiPoint

        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_destination)
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(et_search_dest_search.windowToken, 0)

        setClickListener()

        placeAdapter = PlaceAdapter(placeItems)
        placeAdapter.setOnItemClickListener(this)
        rv_search_dest_search.layoutManager = LinearLayoutManager(this)
        rv_search_dest_search.adapter = placeAdapter
    }

    private fun setClickListener() {
        tv_voice_dest_search.setOnClickListener {
            val intent = Intent(applicationContext, DestinationVoiceInputActivity::class.java)
            startActivity(intent)
        }
        et_search_dest_search.setOnKeyListener { v, keyCode, event ->
            // press enter
            if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                item = tmapdata.findAllPOI(et_search_dest_search.text.toString())
                for (i in 0 until item.size){
                    placeItems.add(item[i].poiName)
                }
                placeAdapter.notifyDataSetChanged()
            }
            true
        }
    }
}
