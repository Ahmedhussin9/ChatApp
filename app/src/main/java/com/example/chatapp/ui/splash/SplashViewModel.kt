package com.example.chatapp.ui.splash

import androidx.lifecycle.ViewModel
import com.example.chatapp.common.SingleLiveEvent
import com.example.chatapp.ui.SessionProvider
import com.example.chatapp.ui.firestore.UsersDao
import com.example.chatapp.ui.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashViewModel:ViewModel() {
    val events = SingleLiveEvent<SplashScreenViewEvents>()
    fun navigate() {
        if(Firebase.auth.currentUser==null){
            events.postValue(SplashScreenViewEvents.NavigateToSignIn)
            return
        }else{
            UsersDao.getUser(Firebase.auth.currentUser?.uid?:""
            ){
                if (it.isSuccessful){
               val user = it.result.toObject(User::class.java)
                    SessionProvider.user = user
                    events.postValue(SplashScreenViewEvents.NavigateToHome)
               }else{
                   events.postValue(SplashScreenViewEvents.NavigateToSignIn)
               }
            }
        }
    }
}