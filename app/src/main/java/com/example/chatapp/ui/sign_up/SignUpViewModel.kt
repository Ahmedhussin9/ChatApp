package com.example.chatapp.ui.sign_up

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.common.SingleLiveEvent
import com.example.chatapp.ui.Message
import com.example.chatapp.ui.SessionProvider
import com.example.chatapp.ui.firestore.UsersDao
import com.example.chatapp.ui.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpViewModel:ViewModel()
{   val events=SingleLiveEvent<SignUpViewEvent>()
    val messageLiveData = SingleLiveEvent<Message>()
    val userName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordConfirmation = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()
    val nameError = MutableLiveData<String?>()
    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()
    val passwordConfirmError = MutableLiveData<String?>()
    val auth = Firebase.auth
    fun singUp(){
        if(!isValidForm()){
            return
        }
        isLoading.postValue(true)
        auth.createUserWithEmailAndPassword(email.value!!,password.value!!).addOnCompleteListener(){task->
            if (task.isSuccessful){
                messageLiveData.postValue(
                    Message(
                        message = "User SignedUp Successfully",
                        posActionName = "Okay",
                        onPosActionClick = {
                            events.postValue(SignUpViewEvent.NavigateToHome)
                        }
                    )
                )
               insertUserIntoFirestore(task.result.user?.uid)
            }else{
                isLoading.postValue(false)
                messageLiveData.postValue(
                    Message(
                    message = task.exception?.localizedMessage
                )
                )
            }
        }
    }

    private fun insertUserIntoFirestore(uid: String?) {
        val user =User(
            id = uid,
            userName =userName.value,
            email =email.value
        )
        UsersDao.createUser(user){
            if(it.isSuccessful){
                SessionProvider.user = user
            }else{
                messageLiveData.postValue(
                    Message(
                        message = it.exception?.localizedMessage
                    )
                )
            }
        }
        isLoading.postValue(false)
    }

    private fun isValidForm(): Boolean {
        var isValid= true
        if (userName.value.isNullOrBlank()){
            isValid = false
            nameError.postValue("Enter A User Name")

        }else{
            nameError.postValue(null)
        }
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
        if (passwordConfirmation.value!=password.value||passwordConfirmation.value.isNullOrBlank()){
            isValid = false
            passwordConfirmError.postValue("Confirm Your Password")
        }else{
            passwordConfirmError.postValue(null)
        }
        return isValid
    }
}