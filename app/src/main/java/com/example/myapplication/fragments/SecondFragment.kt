package com.example.myapplication.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.R
import com.example.myapplication.app.DatabaseMode
import com.example.myapplication.databinding.FragmentSecondBinding
import com.example.myapplication.model.ItemDataClass
import com.example.myapplication.utils.colorsStatusbar

class SecondFragment : BaseFragment() {

    lateinit var binding: FragmentSecondBinding
    var textSize = DatabaseMode.databaseMode!!.getTextSize()

    override fun getLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSecondBinding.inflate(layoutInflater, container, false)
        return binding.root


    }


    override fun onViewReady(view: View) {
        loadMode()
        loadText()
        val bundle = arguments
        //Log.d("BBBB", "onViewReady: ${bundle!!.get("list")}")
        val list: ItemDataClass? = bundle!!.get("list") as ItemDataClass?
        val position: Int = bundle.getInt("position")
        binding.text2.setText(list!![position].text)
        binding.zoomIn.setOnClickListener {
            incTextSize()

        }

        binding.zoomOut.setOnClickListener {
            decTextSize()
        }

        binding.titleCustomActionbar.text = list[position].title


        binding.back.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.settings.setOnClickListener {
            showdialog()
        }
    }

    private fun loadText() {
        binding.text2.textSize = textSize.toFloat()
    }

    private fun decTextSize() {
        if (textSize > 12) {
            textSize--
            binding.text2.setTextSize(textSize.toFloat())
            DatabaseMode.databaseMode!!.saveTextSize(textSize)
        }
    }

    private fun incTextSize() {
        if (textSize >= 12 && textSize < 30) {
            textSize++
            binding.text2.setTextSize(textSize.toFloat())
            DatabaseMode.databaseMode!!.saveTextSize(textSize)
        }
    }

    private fun showdialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val view: View =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_change_mode, null)
        builder.setView(view)
        val dialog: AlertDialog = builder.create()
        dialog.show()

        val add = view.findViewById<ImageView>(R.id.add)
        val minus = view.findViewById<ImageView>(R.id.minus)
        val textSizeText = view.findViewById<TextView>(R.id.text_size)
        textSizeText.setText(textSize.toInt().toString())
        add.setOnClickListener {
            incTextSize()
            textSizeText.setText(textSize.toString())
        }
        minus.setOnClickListener {
            decTextSize()
            textSizeText.setText(textSize.toString())
        }
    }

    private fun loadMode() {
        val mode = DatabaseMode.databaseMode!!.getMode()
        val color = Color.parseColor(DatabaseMode.databaseMode!!.getModeColor())

        if (mode.equals("day")) {
            binding.customActionbar.setBackgroundColor(color)
            binding.secodFragment.setBackgroundColor(Color.WHITE)
        } else {
            binding.customActionbar.setBackgroundColor(Color.parseColor(colorsStatusbar[1]))
            binding.secodFragment.setBackgroundColor(Color.parseColor(colorsStatusbar[0]))
            binding.text2.setTextColor(Color.WHITE)
            binding.titleCustomActionbar.setTextColor(Color.WHITE)
            binding.zoomIn.setImageResource(R.drawable.ic_zoom_in_white)
            binding.zoomOut.setImageResource(R.drawable.ic_zoom_out_white)
            binding.settings.setImageResource(R.drawable.ic_settings_white)
            binding.back.setImageResource(R.drawable.ic_back_white)
        }
    }

}