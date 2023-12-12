package com.example.chatapp.ui.addroom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.chatapp.databinding.ItemRoomCatBinding
import com.example.chatapp.ui.model.Cat

class RoomCatAdapter(val items:List<Cat>):BaseAdapter() {
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return items[position].id.toLong()

    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewHolder:ViewHolder
        if(view==null){
            val itemBinding=ItemRoomCatBinding.inflate(LayoutInflater.from(parent?.context),parent,false)
            viewHolder = ViewHolder(itemBinding)
            itemBinding.root.tag = viewHolder
        }else{
            viewHolder = view.tag as ViewHolder
        }
        viewHolder.bind(items[position])
        return viewHolder.itemBinding.root
    }
    class ViewHolder(val itemBinding:ItemRoomCatBinding){
        fun bind(item:Cat){
            itemBinding.image.setImageResource(item.image)
            itemBinding.title.text= item.title
        }
    }
}