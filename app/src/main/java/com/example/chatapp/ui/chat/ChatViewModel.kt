package com.example.chatapp.ui.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.common.SingleLiveEvent
import com.example.chatapp.ui.SessionProvider
import com.example.chatapp.ui.firestore.MessagesDao
import com.example.chatapp.ui.model.Message
import com.example.chatapp.ui.model.Room
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener

class ChatViewModel:ViewModel() {
    var room:Room?=null
    var messageLiveData = MutableLiveData<String>()
    var toastLiveData = SingleLiveEvent<String>()
    var newMessagesLiveData = SingleLiveEvent<List<Message>>()
    fun sendMessage(){
        if(messageLiveData.value.isNullOrBlank())return
        val message = Message(
            content =messageLiveData.value,
            senderId = SessionProvider.user?.id,
            senderName =SessionProvider.user?.userName ,
            roomId = room?.id,
        )
        MessagesDao.sendMessage(message){task->
            if (task.isSuccessful){
                messageLiveData.value = ""
            }else{
                toastLiveData.postValue("Something went wrong, Try again later")
            }
        }
    }
    fun changeRoom(room: Room?){
        this.room = room
        listenForMessagesInRoom()
    }
    fun listenForMessagesInRoom(){
        MessagesDao.getMessagesCollection(room?.id?:"")
            .orderBy("dateTime")
            .limit(100)
            .addSnapshotListener(EventListener { snapShot, _ ->
                val newMessages= mutableListOf<Message>()
                snapShot?.documentChanges?.forEach {
                    if (it.type==DocumentChange.Type.ADDED){
                        val message = it.document.toObject(Message::class.java)
                        newMessages.add(message)
                    }
                    newMessagesLiveData.value = newMessages
                }
            })

    }
}