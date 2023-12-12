package com.example.chatapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.common.SingleLiveEvent
import com.example.chatapp.ui.Message
import com.example.chatapp.ui.SessionProvider
import com.example.chatapp.ui.firestore.RoomsDao
import com.example.chatapp.ui.model.Room
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeViewModel:ViewModel() {
    val event=SingleLiveEvent<HomeViewEvents>()
    val roomsLiveData = MutableLiveData<List<Room>>()
    val isLoading = MutableLiveData<Message>()
    val messageLiveData = SingleLiveEvent<Message>()

    fun navToAddRoom(){
        event.postValue(HomeViewEvents.NavigateToAddRoom)
    }
    fun loadRooms() {
        RoomsDao.getAllRooms(){
            if(!it.isSuccessful){
                //show message
            }
            else{
                val rooms = it.result.toObjects(Room::class.java)
                roomsLiveData.postValue(rooms)
            }
        }
    }
    fun logOut(){
        messageLiveData.postValue(Message(
            message = "Are you sure?"
            , posActionName = "Yes"
            , onPosActionClick = {
                Firebase.auth.signOut()
                SessionProvider.user = null
                event.postValue(HomeViewEvents.NavigateToSignIn)
            },
            negActionName = "Cancel"

        ))

    }

}