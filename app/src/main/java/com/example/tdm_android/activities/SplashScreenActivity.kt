package com.example.tdm_android.activities

import com.example.tdm_android.R
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.tdm_android.constants.Constants
import com.example.tdm_android.functions.showImageRestApiYesNo


class SplashScreenActivity : AppCompatActivity() {

    lateinit var ivImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        showImageAccordingToOriginAndDelayAndRedirect()
    }
    private fun showImageAccordingToOriginAndDelayAndRedirect() {
        val image = intent.getStringExtra(Constants.IMAGE_STR).toString()
        val answer = intent.getStringExtra(Constants.ANSWER_STR).toString()
        val showGif = intent.getStringExtra(Constants.SHOW_GIF_STR)
        ivImage = findViewById(R.id.ivImage)

        showImageRestApiYesNo(answer, image, ivImage, showGif.toBoolean())

        delayAndRedirectToLogin(8000)
    }

    private fun delayAndRedirectToLogin(delayMillis: Long){
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
            intent.putExtra(Constants.ORIGIN_STR, "SplashScreen")
            startActivity(intent)
            finish() //Para cerrar (matar) la vista que dejo
        }, delayMillis)
    }

}