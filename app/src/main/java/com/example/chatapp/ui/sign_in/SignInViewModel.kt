package com.example.chatapp.ui.sign_in

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.common.SingleLiveEvent
import com.example.chatapp.ui.Message
import com.example.chatapp.ui.SessionProvider
import com.example.chatapp.ui.firestore.UsersDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInViewModel:ViewModel()
{
     val email = MutableLiveData<String>()
     val emailError = MutableLiveData<String?>()
     val password = MutableLiveData<String>()
     val passwordError = MutableLiveData<String?>()
     val isLoading = MutableLiveData<Boolean>()
     val messageLiveData = SingleLiveEvent<Message>()
     val event = SingleLiveEvent<SignInViewEvent>()
     val auth = Firebase.auth
    fun singIn(){
        if(!isValidForm()){
            return
        }
        isLoading.postValue(true)
        auth.signInWithEmailAndPassword(email.value!!,password.value!!).addOnCompleteListener(){
            isLoading.postValue(false)
            if (it.isSuccessful){
                getUserFromFirestore(it.result.user?.uid)
                messageLiveData.postValue(
                    Message(
                        message = "logged in successfully",
                        posActionName = "ok",
                        onPosActionClick = {
                            event.postValue(SignInViewEvent.NavigateToHome)
                        },
                    ))
            }else{
                messageLiveData.postValue(
                    Message(
                    message = it.exception?.localizedMessage
                )
                )
            }
        }
    }

    private fun getUserFromFirestore(uid: String?) {
        UsersDao.getUser(uid){task->
            isLoading.postValue(false)
            if (task.isSuccessful){
                val user = task.result.toObject(com.example.chatapp.ui.model.User::class.java)
                SessionProvider.user =user
            }else{
                messageLiveData.postValue(
                    Message(
                        message = task.exception?.localizedMessage
                    )
                )
            }
        }
    }

    private fun isValidForm(): Boolean {
        var isValid= true
        if (email.value.isNullOrBlank()){
            isValid = false
            emailError.postValue("Enter An Email")
        }else{
            emailError.postValue(null)
        }
        if (password.value.isNullOrBlank()){
            isValid = false
            passwordError.postValue("Enter A Password")

        }else{
            passwordError.postValue(null)
        }
        return isValid
    }
}