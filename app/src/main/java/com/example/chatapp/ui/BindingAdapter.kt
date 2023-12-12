package com.example.chatapp.ui

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:Error")
fun bindErrorInTextInputLayout(textInputLayout: TextInputLayout,error:String?){
    textInputLayout.error = error
}