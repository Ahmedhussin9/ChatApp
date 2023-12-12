package com.example.chatapp.ui

fun interface OnDialogActionClick{
    fun onActionClick()
}
data class Message (
    val message:String? = null,
    val throwable: Throwable?=null,
    val posActionName:String?=null,
    val negActionName:String?=null,
    val onPosActionClick: OnDialogActionClick?=null,
    val onNegActionClick: OnDialogActionClick?=null,
    val isCancelable:Boolean = true
    )