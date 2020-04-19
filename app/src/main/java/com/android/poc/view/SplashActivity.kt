package com.android.poc.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.android.poc.R
import kotlinx.android.synthetic.main.splash_activity.*


/**
 * this is a  splash screen which is  first interactive page shown on app for 3 seconds
 * */
public class SplashActivity : AppCompatActivity() {
    private val SPLASH_SCREEN_TIME_OUT: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        //This method is used so that your splash activity
        //can cover the entire screen.
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        //this will bind your Splash with activity_main.
        Handler().postDelayed(Runnable {
            val i = Intent(
                this,
                LandingActivity::class.java
            )
            //Intent is used to switch from one activity to another.
            startActivity(i)
            //invoke the SecondActivity.
            finish()
            //the current activity will get finished.
        }, SPLASH_SCREEN_TIME_OUT)
    }

}