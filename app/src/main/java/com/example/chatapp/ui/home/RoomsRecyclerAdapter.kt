package com.example.chatapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.databinding.ItemRoomBinding
import com.example.chatapp.ui.model.Cat
import com.example.chatapp.ui.model.Room

class RoomsRecyclerAdapter(var rooms:List<Room>? = listOf()):RecyclerView.Adapter<RoomsRecyclerAdapter.ViewHolder>() {

    class ViewHolder(val itemRoomBinding: ItemRoomBinding):RecyclerView.ViewHolder(itemRoomBinding.root){
        fun bind(room: Room?){
            itemRoomBinding.title.text = room?.title
            itemRoomBinding.image.setImageResource(Cat.getCatImageById(room?.catId))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemRoomBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return rooms?.size ?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(rooms?.get(position))

        onItemClickListener?.let {
            holder.itemView.setOnClickListener(){view->
                it.onItemCLick(position=position, room = rooms!![position])
            }
        }

    }

    fun changeData(rooms: List<Room>?) {
        this.rooms = rooms
        notifyDataSetChanged()
    }
    var onItemClickListener:OnItemClickListenter?=null
    fun interface OnItemClickListenter{
        fun onItemCLick(position: Int,room:Room)
    }
}