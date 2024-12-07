package com.dicoding.cektandur.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.cektandur.R
import com.dicoding.cektandur.data.pref.UserPreferences
import com.dicoding.cektandur.ui.welcome.WelcomeActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        userPreferences = UserPreferences.getInstance(this)

        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launch {
                val isLogin = userPreferences.isLogin.first()
                val intent = if (isLogin) {
                    Intent(this@SplashScreenActivity, MainActivity::class.java)
                } else {
                    Intent(this@SplashScreenActivity, WelcomeActivity::class.java)
                }
                startActivity(intent)
                finish()
            }
        }, 4000)
    }
}