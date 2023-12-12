package com.example.chatapp.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    val id:String?=null,
    val title:String?=null,
    val description:String?=null,
    val catId:Int?=null,
    val ownerId:String?=null,
    val image:Int?=null
):Parcelable{
    companion object {
        const val CollectionName = "rooms"
    }
}
