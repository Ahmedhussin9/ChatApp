package com.example.chatapp.ui.sign_in

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.databinding.ActivitySignInBinding
import com.example.chatapp.ui.home.HomeActivity
import com.example.chatapp.ui.showMessage
import com.example.chatapp.ui.sign_up.SignUp

class SignIn : AppCompatActivity() {
    lateinit var viewBinding: ActivitySignInBinding
    lateinit var viewModel: SignInViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewModel.messageLiveData.observe(this){message->
            showMessage(
                message = message.message?:"Something Went Wrong",
                posActionName = "Okay",
                negActionName = "cancel",
                posAction = message.onPosActionClick,
                negAction = message.onNegActionClick,
                isCancellable = message.isCancelable
            )
        }
        viewModel.event.observe(this,::handelViewEvent)
    }

    private fun handelViewEvent(signInViewEvent: SignInViewEvent?) {
        when(signInViewEvent){
             SignInViewEvent.NavigateToHome->{
                 navToHome()
             }
            SignInViewEvent.NavigateToSignUp->{
                navToSignUp()
            }

            else -> {}
        }
    }
    private fun navToSignUp() {
        val intent = Intent(this,SignIn::class.java)
        startActivity(intent)
        finish()
    }

    private fun navToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initViews() {
        viewBinding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.content.dontHaveAccountText.setOnClickListener(){
            startSignUpActivity()
        }
        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]
        viewBinding.lifecycleOwner = this
        viewBinding.vm = viewModel
    }

    private fun startSignUpActivity() {
        intent = Intent(this, SignUp::class.java)
        startActivity(intent)
        finish()
    }
}