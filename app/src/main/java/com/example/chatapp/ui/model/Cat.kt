package com.example.chatapp.ui.model

import com.example.chatapp.R

data class Cat(
    val id:Int,
    val title:String,
    val image:Int
){
    companion object{
        fun getCat()= listOf<Cat>(
            Cat(1,"Sports", R.drawable.sports),
            Cat(2,"Movies", R.drawable.movies),
            Cat(3,"Music", R.drawable.music)
        )

        fun getCatImageById(id: Int?):Int{
            return when(id){
                1->{
                    R.drawable.sports
                }
                2->{
                    R.drawable.music

                }
                3->{
                    R.drawable.movies
                }
                else -> {
                    R.drawable.sports
                }
            }
        }
    }

}
