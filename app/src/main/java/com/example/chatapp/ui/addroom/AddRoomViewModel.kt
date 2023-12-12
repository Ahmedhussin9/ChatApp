package com.example.chatapp.ui.addroom

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.common.SingleLiveEvent
import com.example.chatapp.ui.Message
import com.example.chatapp.ui.SessionProvider
import com.example.chatapp.ui.firestore.RoomsDao
import com.example.chatapp.ui.model.Cat

class AddRoomViewModel:ViewModel() {
    val loading = MutableLiveData<Message?>()
    val message = SingleLiveEvent<Message>()
    val events = SingleLiveEvent<AddRoomViewEvents>()
    val roomNameError = MutableLiveData<String?>()
    val roomDescriptionError= MutableLiveData<String?>()
    val roomNameText= MutableLiveData<String>()
    val roomDescriptionText = MutableLiveData<String>()
    val cats = Cat.getCat()
    var selectedCat = cats[0]

    private fun validForm(): Boolean {
        var isValid = true
        if(roomNameText.value.isNullOrBlank()){
            roomNameError.postValue("Please Enter room title ")
            isValid =false

        }else{
            roomNameError.postValue(null)
        }
        if(roomDescriptionText.value.isNullOrBlank()){
            roomDescriptionError.postValue("Please Enter room desc ")
            isValid =false
        }else{
            roomDescriptionError.postValue(null)
        }
        return isValid
    }

    fun createRoom(){
        if(!validForm())return
        loading.postValue(Message(
            message ="Loading...",
            isCancelable = false
        ))
        RoomsDao.createRoom(
            title = roomNameText.value?:"",
            desc = roomDescriptionText.value?:"",
            ownerId =SessionProvider.user?.id?:"" ,
            catId = selectedCat.id,
        ){
            if (it.isSuccessful){
                loading.postValue(null)
                message.postValue(
                    Message(message = "Room Created"
                        , posActionName = "ok"
                        , onPosActionClick = {
                            events.postValue(AddRoomViewEvents.NavigateToHomeAndFinish)
                        })
                )
            }else{
                message.postValue(
                    Message("Something went Wrong"

                    )

                )
            }
        }
    }

    fun onCatSelected(position: Int) {
        selectedCat= cats[position]
    }

}