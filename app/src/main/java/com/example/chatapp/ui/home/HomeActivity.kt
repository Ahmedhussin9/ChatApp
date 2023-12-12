package com.example.chatapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityHomeBinding
import com.example.chatapp.ui.Constants
import com.example.chatapp.ui.addroom.AddRoomAcivity
import com.example.chatapp.ui.chat.ChatActivity
import com.example.chatapp.ui.model.Room
import com.example.chatapp.ui.showLoadingProgressDialog
import com.example.chatapp.ui.showMessage
import com.example.chatapp.ui.sign_in.SignIn

class HomeActivity : AppCompatActivity() {
    private lateinit var viewBinding : ActivityHomeBinding
    val viewModel:HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding =DataBindingUtil.setContentView(this, R.layout.activity_home)
        initViews()
        subscribeToliveData()
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadRooms()
    }
    var loadingDialog:android.app.AlertDialog?=null
    private fun subscribeToliveData() {
        viewModel.event.observe(this,::HandleEvent)
        viewModel.roomsLiveData.observe(this){
            adapter.changeData(it)
        }
        viewModel.messageLiveData.observe(this){message->
            showMessage(
                message = message.message?:"Something Went Wrong",
                posActionName = "Okay",
                negActionName = "cancel",
                posAction = message.onPosActionClick,
                negAction = message.onNegActionClick,
                isCancellable = message.isCancelable
            )
        }
        viewModel.isLoading.observe(this){
            if (it==null){
                //hide
                loadingDialog?.dismiss()
                loadingDialog = null

            }else{
                loadingDialog =showLoadingProgressDialog(it.message,it.isCancelable)
                loadingDialog?.show()
            }
        }
    }

    private fun HandleEvent(homeViewEvents: HomeViewEvents?) {
        when(homeViewEvents){
            HomeViewEvents.NavigateToAddRoom->{
                navToAddRoom()
            }
            HomeViewEvents.NavigateToSignIn->{
                navToSignIn()
            }
            else -> {}
        }
    }

    private fun navToAddRoom() {
        intent = Intent(this,AddRoomAcivity::class.java)
        startActivity(intent)
    }
    private fun navToSignIn() {
        intent = Intent(this,SignIn::class.java)
        startActivity(intent)
        finish()
    }
    val adapter = RoomsRecyclerAdapter()
    private fun initViews() {
        viewBinding.vm=viewModel
        viewBinding.lifecycleOwner = this
        viewBinding.content.roomsRecyclerView.adapter = adapter
        adapter.onItemClickListener =RoomsRecyclerAdapter.OnItemClickListenter{
            position, room ->
            navigateToRoom(room)
        }
    }

    private fun navigateToRoom(room:Room) {
        var intent  = Intent(this,ChatActivity::class.java)
        intent.putExtra(Constants.EXTRA_ROOM,room)
        startActivity(intent)
    }
}