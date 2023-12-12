package com.example.chatapp.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog

fun Activity.showMessage(message:String,
                         posActionName:String?=null,
                         posAction:OnDialogActionClick?=null,
                         negActionName:String?=null,
                         negAction:OnDialogActionClick?=null,
                         isCancellable:Boolean = true
):AlertDialog{
    val dialogBuilder = AlertDialog.Builder(this)
    dialogBuilder.setMessage(message)
    if (posActionName!=null){
        dialogBuilder.
        setPositiveButton(posActionName) { dialog, which ->
            dialog.dismiss()
            posAction?.onActionClick()
        }
    }
    if(negActionName!=null){
        dialogBuilder.setNegativeButton(negActionName){dialog,which->
            dialog.dismiss()
            negAction?.onActionClick()
        }
    }
    dialogBuilder.setCancelable(isCancellable)
    return dialogBuilder.show()

}

fun Activity.showLoadingProgressDialog(message:String?,isCancellable: Boolean):AlertDialog{
    val alertDialog=ProgressDialog(this)
    alertDialog.setMessage(message)
    alertDialog.setCancelable(isCancellable)
    return alertDialog
}