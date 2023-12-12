package com.example.chatapp.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chatapp.databinding.ItemRecivedMessageBinding
import com.example.chatapp.databinding.ItemSentMessageBinding
import com.example.chatapp.ui.SessionProvider
import com.example.chatapp.ui.model.Message

class MessagesAdapter(var messages:MutableList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class SentMessageViewHolder(val itemBinding:ItemSentMessageBinding):ViewHolder(itemBinding.root){
        fun bind(message:Message){
            itemBinding.setMessage(message)
            itemBinding.executePendingBindings()
        }
    }
    class RecivedMessageViewHolder(val itemBinding:ItemRecivedMessageBinding):ViewHolder(itemBinding.root){
        fun bind(message:Message){
            itemBinding.setMessage(message)
            itemBinding.executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages.get(position)
        if (message.senderId==SessionProvider.user?.id){
            return MessageType.Sent.value
        }else{
            return MessageType.Received.value
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == MessageType.Sent.value){
            val itemBinding = ItemSentMessageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return SentMessageViewHolder(itemBinding)
        }else{
            val itemBinding = ItemRecivedMessageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return RecivedMessageViewHolder(itemBinding)
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is SentMessageViewHolder){
            holder.bind(messages[position])
        }else if (holder is RecivedMessageViewHolder){
            holder.bind(messages[position])
        }
    }

    fun addNewMessages(newMessages: List<Message>?) {
        if(newMessages==null)return
        val oldSize = messages.size
        messages.addAll(newMessages)
        notifyItemRangeInserted(oldSize,newMessages.size)
    }
}

enum class MessageType(val value:Int){
    Sent(100),
    Received(200)
}