package com.example.chatapp.ui.firestore

import com.example.chatapp.ui.model.Message
import com.google.android.gms.tasks.OnCompleteListener

object MessagesDao {
    fun getMessagesCollection(roomId:String)=
        RoomsDao.getRoomsCollection().document(roomId).collection(Message.CollectionName)

    fun sendMessage(message: Message,onCompleteListener: OnCompleteListener<Void>){
      val messagesDocument=getMessagesCollection(message.roomId?:"").document()
        message.id = messagesDocument.id
        messagesDocument.set(message).addOnCompleteListener(onCompleteListener)
    }
}