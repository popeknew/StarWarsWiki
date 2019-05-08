package com.example.nbainfoapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.nbainfoapp.R
import com.example.nbainfoapp.RestApi
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        setupAnimation()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        startActivity(Intent(this, NavigationActivity::class.java))
        finish()
        return true
    }

    private fun setupAnimation() {
        val fadeAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.fadein_anim)
        logo.animation = fadeAnimation
    }
}
