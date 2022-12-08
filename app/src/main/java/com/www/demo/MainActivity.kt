package com.www.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rotateView = findViewById<RotateView>(R.id.rotateView)
        rotateView.animator.start()

        var circleRotateView = findViewById<CircleRotateView>(R.id.circleView)
        circleRotateView.animator.start()

    }
}