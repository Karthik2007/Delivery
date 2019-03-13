package com.karthik.delivery.base.view.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.karthik.delivery.R
import com.karthik.delivery.deliverylist.view.DeliveryListActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(applicationContext, DeliveryListActivity::class.java))
            finish()
        }, 5000)


    }

    override fun onResume() {
        super.onResume()
        animateSplashImage()
    }


    private fun animateSplashImage() {
        val bounceAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        splash_image.startAnimation(bounceAnimation)
    }
}
