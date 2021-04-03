package com.example.myapplication.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.app.DatabaseMode
import com.example.myapplication.databinding.FragmentBlankBinding
import com.example.myapplication.utils.colorsStatusbar


class BlankFragment : BaseFragment() {
    lateinit var binding: FragmentBlankBinding
    var textsize = DatabaseMode.databaseMode!!.getTextSize()
    override fun getLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlankBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    @SuppressLint("SetTextI18n")
    override fun onViewReady(view: View) {
        loadMode()
        binding.aboutWritertxt.setText(R.string.muallif_haqida)
        binding.titleAbout.setText("Muallif")

        binding.back.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun loadMode() {
        binding.aboutWritertxt.setTextSize(textsize.toFloat())
        val mode = DatabaseMode.databaseMode!!.getMode()
        val color = DatabaseMode.databaseMode!!.getModeColor()
        val parceColor = Color.parseColor(color)
        val night1 = Color.parseColor(colorsStatusbar[0])
        val night2 = Color.parseColor(colorsStatusbar[1])


        if (mode.equals("night")) {
            binding.aboutWritertxt.setTextColor(Color.WHITE)
            binding.customActionbar.setBackgroundColor(night2)
            binding.back.setImageResource(R.drawable.ic_back_white)
            binding.backBlank.setBackgroundColor(night1)

        } else {
            binding.customActionbar.setBackgroundColor(parceColor)
        }
    }


}