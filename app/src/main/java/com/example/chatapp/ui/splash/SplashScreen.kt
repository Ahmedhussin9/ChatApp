package com.example.chatapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.databinding.SplashScreenBinding
import com.example.chatapp.ui.home.HomeActivity
import com.example.chatapp.ui.sign_in.SignIn

class SplashScreen : AppCompatActivity() {
    lateinit var viewBinding:SplashScreenBinding
    val viewModel:SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        subscribeToLiveData()
        startSignUpActivity()
    }

    private fun subscribeToLiveData() {
        viewModel.events.observe(this){
            when(it){
                SplashScreenViewEvents.NavigateToHome->{
                    navigateToHome()
                }
                SplashScreenViewEvents.NavigateToSignIn->{
                    navigateToSignIn()
                }
            }
        }
    }

    private fun navigateToSignIn() {
        val intent = Intent(this,SignIn::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToHome() {
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startSignUpActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.navigate()
        },2000)
    }


}