package com.example.myapplication.fragments

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.res.Configuration
import android.graphics.Color

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.app.DatabaseMode
import com.example.myapplication.databinding.FragmentSettingsBinding
import com.example.myapplication.utils.colorsStatusbar
import com.example.myapplication.utils.ibratli_sozlar_krill
import com.example.myapplication.utils.ibratlisozlar
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.OnColorChangedListener
import com.flask.colorpicker.OnColorSelectedListener
import com.flask.colorpicker.builder.ColorPickerClickListener
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.google.android.material.slider.Slider
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random


class SettingsFragment : BaseFragment(), Slider.OnSliderTouchListener, Slider.OnChangeListener,
    View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    lateinit var binding: FragmentSettingsBinding
    override fun getLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewReady(view: View) {

        //  binding.slider.addOnSliderTouchListener(this)
        binding.slider.addOnChangeListener(this)
        binding.colorPicker.setOnClickListener(this)

        binding.backSetting.setOnClickListener {
            activity?.onBackPressed()
        }
        loadSimpleText()



        loadMode()
        loadText()
        loadLangMode()
        binding.radioGroup.setOnCheckedChangeListener(this)

    }

    private fun loadLangMode() {


        val langMode = DatabaseMode.databaseMode!!.getModeLang()
        if (langMode.equals("en")) {
            (binding.radioGroup[0] as RadioButton).isChecked = true
        } else {
            (binding.radioGroup[1] as RadioButton).isChecked = true
        }
    }

    private fun loadSimpleText() {
        val langMode = DatabaseMode.databaseMode!!.getModeLang()
        var list: ArrayList<String>? = null
        if (langMode.equals("en")) {
            list = ibratlisozlar
        } else {
            list = ibratli_sozlar_krill
        }

        val k = Random.nextInt(0, list.size)
        binding.simpleText.setText(list[k])
    }

    private fun loadText() {
        val textSize = DatabaseMode.databaseMode!!.getTextSize()
        binding.simpleText.setTextSize(textSize.toFloat())
        binding.textSettings.setText(textSize.toString())
        binding.slider.value = textSize.toFloat()

    }

    @SuppressLint("ResourceType")
    private fun loadMode() {
        val mode = DatabaseMode.databaseMode!!.getMode()
        val color = DatabaseMode.databaseMode!!.getModeColor()
        val parceColor = Color.parseColor(color)
        val night1 = Color.parseColor(colorsStatusbar[0])
        val night2 = Color.parseColor(colorsStatusbar[1])

        if (mode.equals("night")) {
            //     binding.circleView.setBackgroundColor(Color.parseColor(colorsStatusbar[1]))
            binding.circleView.circleColor = Color.parseColor(colorsStatusbar[1])
            binding.circleView2.circleColor = Color.parseColor(colorsStatusbar[0])
            binding.customActionbar.setBackgroundColor(Color.parseColor(colorsStatusbar[1]))
            binding.settingsBack.setBackgroundColor(night1)
            binding.textSettings.setTextColor(Color.WHITE)
            binding.backSetting.setImageResource(R.drawable.ic_back_white)
            binding.colorIndicatorText.setTextColor(Color.WHITE)
            binding.simpleCard.setCardBackgroundColor(night1)
            binding.simpleText.setTextColor(Color.WHITE)
            binding.colorIndicatorText.setTextColor(Color.WHITE)
            binding.slider.setBackgroundColor(night1)
            binding.textDesc.setTextColor(Color.WHITE)
            binding.nameWriter.setTextColor(Color.WHITE)
        } else {
            binding.circleView.circleColor = parceColor
            activity?.window!!.statusBarColor = parceColor
            activity?.window!!.navigationBarColor = parceColor
            binding.customActionbar.setBackgroundColor(parceColor)
            binding.circleView2.circleColor = Color.WHITE
            binding.nameWriter.setTextColor(Color.BLACK)
        }


    }

    override fun onStartTrackingTouch(slider: Slider) {
        binding.simpleText.setTextSize(slider.value)
    }

    override fun onStopTrackingTouch(slider: Slider) {
        binding.simpleText.setTextSize(slider.value)


    }

    override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
        binding.simpleText.setTextSize(slider.value)
        binding.textSettings.setText(slider.value.toInt().toString())
        DatabaseMode.databaseMode!!.saveTextSize(slider.value.toInt())
    }


    override fun onClick(v: View?) {
        ColorPickerDialogBuilder.with(requireContext())
            .setTitle(resources.getString(R.string.fon_ranginitanlang))
            .initialColor(Color.parseColor(colorsStatusbar[0]))
            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
            .density(12)
            .setOnColorChangedListener(object : OnColorChangedListener {
                override fun onColorChanged(selectedColor: Int) {
                    Log.d(
                        "ColorPicker",
                        "onColorChanged: 0x${Integer.toHexString(selectedColor)} "
                    )
                }
            })
            .setOnColorSelectedListener(object : OnColorSelectedListener {
                override fun onColorSelected(selectedColor: Int) {
                    //makeToast("onColorSelected: 0x" + Integer.toHexString(selectedColor))
                }

            })
            .setPositiveButton("ok", object : ColorPickerClickListener {
                override fun onClick(
                    d: DialogInterface?,
                    lastSelectedColor: Int,
                    allColors: Array<out Int>?
                ) {
                    val mode = DatabaseMode.databaseMode!!.getMode()
                    val color = "#" + Integer.toHexString(lastSelectedColor)
                    DatabaseMode.databaseMode!!.saveModeColor(color)
                    binding.circleView.circleColor = Color.parseColor(color)
                    if (mode.equals("nigth")) {
                        makeToast("Agar fon rangini o`zgartirmoqchi bo'lsangiz tungi rejimdan chiqing")
                    }
                    loadMode()

                }

            })
            .setNegativeButton("cancel", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog!!.dismiss()
                }

            })
            .showColorEdit(true)
            .setColorEditTextColor(Color.BLUE)
            .build()
            .show()
    }

    private fun makeToast(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {

        when (checkedId) {
            R.id.uzbekcha -> {
                makeToast("Lotin")
                DatabaseMode.databaseMode!!.saveClickMode(true)
                setLocale("en")
                findNavController().navigate(R.id.homeFragment)
            }
            R.id.krill -> {
                makeToast("krill")
                DatabaseMode.databaseMode!!.savedModeLang("ru")
                setLocale("ru")
                findNavController().navigate(R.id.homeFragment)

            }
        }
    }

    private fun setLocale(lang: String) {
        val locale: Locale = Locale(lang)
        Locale.setDefault(locale)
        val config: Configuration = Configuration()
        config.locale = locale
        requireContext().resources.updateConfiguration(
            config,
            requireContext().resources.displayMetrics
        )
        DatabaseMode.databaseMode!!.savedModeLang(lang)
    }

}