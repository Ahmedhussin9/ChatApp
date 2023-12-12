package com.example.chatapp.ui.sign_up

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.databinding.ActivitySignUpBinding
import com.example.chatapp.ui.home.HomeActivity
import com.example.chatapp.ui.showMessage
import com.example.chatapp.ui.sign_in.SignIn

class SignUp : AppCompatActivity() {
    lateinit var viewBinding:ActivitySignUpBinding
    lateinit var viewModel:SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        subscribeToLiveData()
    }

    private fun handleViewEvent(registerViewEvent: SignUpViewEvent?) {
        when(registerViewEvent){
            SignUpViewEvent.NavigateToHome ->{
                navToHome()
            }
            SignUpViewEvent.NavigateToSignIn->{
                navToSignIn()
            }
            else -> {}
        }
    }

    private fun subscribeToLiveData() {
        viewModel.messageLiveData.observe(this){
            showMessage(
                message = it.message?:"Something Went Wrong",
                posAction = it.onPosActionClick,
                posActionName = "Okay",
                negAction = it.onNegActionClick,
                negActionName = it.negActionName,
                isCancellable = it.isCancelable
                )
        }
        viewModel.events.observe(this,::handleViewEvent)
    }

    private fun navToSignIn() {
        val intent = Intent(this,SignIn::class.java)
        startActivity(intent)
        finish()
    }

    private fun navToHome() {
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initViews() {
        viewBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.signUpContent.alreadyHaveOneText.setOnClickListener(){
            startLoginActivity()
        }
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        viewBinding.lifecycleOwner = this
        viewBinding.vm = viewModel
    }

    private fun startLoginActivity() {
        intent = Intent(this, SignIn::class.java)
        startActivity(intent)
        finish()
    }
}