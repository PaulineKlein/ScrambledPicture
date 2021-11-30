package com.pklein.scrambledpicture.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.pklein.scrambledpicture.R
import com.pklein.scrambledpicture.databinding.ActivitySplashBinding
import com.pklein.scrambledpicture.presentation.home.HomeActivity
import com.pklein.scrambledpicture.utils.uiDelayed

const val SPLASH_TIME: Long = 2000

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val splashViewModel: SplashViewModel by lazy {
        ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashViewModel.synchronisedGameData(this)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val rotateAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.progress_anim)
        rotateAnim.duration = SPLASH_TIME
        binding.ivPuzzle.startAnimation(rotateAnim)

        uiDelayed(SPLASH_TIME) {
            openMain()
        }
    }

    private fun openMain() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}