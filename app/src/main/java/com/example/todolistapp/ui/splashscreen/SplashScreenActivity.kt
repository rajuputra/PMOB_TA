package com.example.todolistapp.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todolistapp.databinding.ActivitySplashScreenBinding
import com.example.todolistapp.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.splashLogo.alpha= 0f

        binding.splashLogo.animate().setDuration(3000).alpha(1f).withEndAction{
            val x = Intent(this, MainActivity::class.java)
            startActivity(x)

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

    }

}