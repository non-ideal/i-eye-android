package com.maas.soft.i_eye.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.maas.soft.i_eye.R
import kotlinx.android.synthetic.main.activity_delete_favorites.*

class DeleteFavoritesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_favorites)
        setClickListenrs()
    }

    private fun setClickListenrs(){
        btn_yes_delete_favorites.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
            finish()
        }
        btn_no_delete_favorites.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
            finish()
        }
    }
}
