package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.app.DatabaseMode
import com.example.myapplication.databinding.ItemDataBinding
import com.example.myapplication.databinding.ItemDataNigthBinding
import com.example.myapplication.model.ItemDataClass
import com.example.myapplication.model.ItemDataClassItem
import com.example.myapplication.utils.IOnItemClickListener

class AdapterItem : RecyclerView.Adapter<AdapterItem.VH>() {
    var list: ItemDataClass? = null
    var listener: IOnItemClickListener? = null

    lateinit var binding: ItemDataBinding
    lateinit var bindingN: ItemDataNigthBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val mode = DatabaseMode.databaseMode!!.getMode()
        binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        bindingN = ItemDataNigthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        if (mode.equals("day")) {
            return VH(
                LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
            )
        } else {
            return VH(
                LayoutInflater.from(parent.context).inflate(R.layout.item_data_nigth, parent, false)
            )
        }


    }

    override fun getItemCount(): Int {
        list?.let {
            return it.size
        }
        return 0

    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list!![position])
        holder.itemView.setOnClickListener { listener?.onItemClick(list!!, position) }


    }

    inner class VH(var v: View) : RecyclerView.ViewHolder(v) {
        var title = v.findViewById<TextView>(R.id.title)
        var desc = v.findViewById<TextView>(R.id.desc)
        fun onBind(data: ItemDataClassItem) {
            title.text = data.title
            desc.text = data.description
        }
    }

}