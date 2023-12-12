package com.example.chatapp.ui.addroom

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityAddRoomBinding
import com.example.chatapp.ui.home.HomeActivity
import com.example.chatapp.ui.showLoadingProgressDialog
import com.example.chatapp.ui.showMessage

class AddRoomAcivity : AppCompatActivity() {
    lateinit var viewBinding:ActivityAddRoomBinding
    val viewModel:AddRoomViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_room)
        initViews()
        subscribeToLiveData()
        }
    var loadingDialog:AlertDialog?=null
    private fun subscribeToLiveData() {
        viewModel.message.observe(this){showMessage(it.message?:"", posActionName = it.posActionName, posAction = it.onPosActionClick, negAction = it.onNegActionClick, negActionName = it.negActionName, isCancellable = it.isCancelable)}
        viewModel.events.observe(this,::handleEvents)
        viewModel.loading.observe(this){
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

    private fun handleEvents(addRoomViewEvents: AddRoomViewEvents?) {
        when(addRoomViewEvents){
            AddRoomViewEvents.NavigateToHomeAndFinish->{
                navToHomeAndFinish()
            }
            else -> {}
        }
    }

    private fun navToHomeAndFinish() {
        intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    lateinit var catAdapter:RoomCatAdapter
    private fun initViews() {
        catAdapter=RoomCatAdapter(viewModel.cats)
        viewBinding.vm = viewModel
        viewBinding.lifecycleOwner = this
        viewBinding.content.catSpinner.adapter = catAdapter
        viewBinding.content.catSpinner.onItemSelectedListener =
            object :AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.onCatSelected(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }
    }
}