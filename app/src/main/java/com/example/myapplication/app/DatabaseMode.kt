package com.example.myapplication.app

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import com.example.myapplication.utils.colorsStatusbar
import java.lang.StringBuilder
import java.util.prefs.Preferences

class DatabaseMode(context: Context) {


    companion object {
        var preferences: SharedPreferences? = null
        var databaseMode: DatabaseMode? = null

        fun init(context: Context) {
            if (databaseMode == null) {
                databaseMode = DatabaseMode(context)
                preferences = context.getSharedPreferences("mymode", Context.MODE_PRIVATE)
            }

        }

    }

    fun getDataBase(): DatabaseMode? {
        return databaseMode
    }

    fun saveMode(mode: String): Boolean {
        return preferences!!.edit().putString("mode", mode).commit()
    }

    fun getMode(): String? {
        return preferences!!.getString("mode", "day")
    }

    fun saveClickMode(clicked: Boolean): Boolean {
        return preferences!!.edit().putBoolean("modeclick", clicked).commit()
    }

    fun getClickMode(): Boolean? {
        return preferences!!.getBoolean("modeclick", true)
    }

    fun saveModeColor(color: String): Boolean {
        return preferences!!.edit().putString("color", color).commit()
    }

    fun getModeColor(): String? {
        return preferences!!.getString("color", "#550000")
    }

    fun saveTextSize(textSize: Int): Boolean {
        return preferences!!.edit().putInt("textsize", textSize).commit()
    }

    fun getTextSize(): Int {
        return preferences!!.getInt("textsize", 18)
    }

    fun savedModeLang(name: String): Boolean {
        return preferences!!.edit().putString("language", name).commit()
    }

    fun getModeLang():String?{
        return preferences!!.getString("language","uz")
    }

    fun saveCheckLangMode(checked:Boolean):Boolean{
        return preferences!!.edit().putBoolean("checkLangMode", checked).commit()
    }
    fun getCheckLangMode():Boolean{
        return preferences!!.getBoolean("checkLangMode",false)
    }


}