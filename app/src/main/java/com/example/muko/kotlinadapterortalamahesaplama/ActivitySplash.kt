package com.example.muko.kotlinadapterortalamahesaplama

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.splash_activity.*

class ActivitySplash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        var buttonUpAndDown = AnimationUtils.loadAnimation(this, R.anim.buttonupanddown)
        var imageUpAndDown = AnimationUtils.loadAnimation(this, R.anim.imageupanddown)

        btn_basla.animation = buttonUpAndDown

        btn_basla.setOnClickListener {

            img_android.startAnimation(imageUpAndDown) //çünkü butona tıklatıldığında bir animasyon başlatıyorum. Bu yüzden burası startAnimation oldu.
            object : CountDownTimer(1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {

                }

                override fun onFinish() {
                    var intent = Intent(applicationContext, MainActivity::class.java) //this ile ulaşamıyoruz bu yüzden ActivityContext
                    startActivity(intent)
                }

            }.start()

        }
    }
}
