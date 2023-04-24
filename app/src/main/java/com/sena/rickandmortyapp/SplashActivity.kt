package com.sena.rickandmortyapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.sena.rickandmortyapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        val sharedPref: SharedPreferences = getSharedPreferences("sharedPref", AppCompatActivity.MODE_PRIVATE)
        val isFirstLogin = sharedPref.getBoolean("isFirstLogin",true)
        var splashMessage = getString(R.string.hello_message)

        if(isFirstLogin){
            val editor = sharedPref.edit()
            editor.putBoolean("isFirstLogin",false)
            editor.apply()
            splashMessage = getString(R.string.welcome_message)
        }

        binding.apply {
            supportActionBar?.hide()
            txtTitle.text = splashMessage
            setContentView(root)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}