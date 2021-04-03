package com.example.myapplication.utils

import com.example.myapplication.model.ItemDataClass
import java.text.FieldPosition

interface IOnItemClickListener {
    fun onItemClick(list: ItemDataClass,position:Int)
}