package com.example.myapplication.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.adapter.AdapterItem
import com.example.myapplication.app.DatabaseMode
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.model.ItemDataClass
import com.example.myapplication.utils.IOnItemClickListener
import com.example.myapplication.utils.colorsStatusbar
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson


@Suppress("UNREACHABLE_CODE")
class HomeFragment : BaseFragment(), IOnItemClickListener,
    NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding: FragmentHomeBinding

    private var list: ItemDataClass? = null
    private var adapter: AdapterItem? = null
    private var mode: ImageView? = null
    lateinit var nav: View
    private var button: ImageView? = null
    private var backheader: LinearLayout? = null


    override fun getLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
        loadMode()

    }

    @SuppressLint("WrongConstant")
    override fun onViewReady(view: View) {


        nav = binding.navViewHome.getHeaderView(0)
        button = nav.findViewById(R.id.mode) as ImageView
        backheader = nav.findViewById(R.id.back_header) as LinearLayout
        mode = view.findViewById(R.id.mode)
        list = gendataAssets()


        loadMode()




        binding.drawMenu.setOnClickListener {
            binding.drawer.openDrawer(Gravity.START)
        }
        binding.navViewHome.setNavigationItemSelectedListener(this)



        button?.setOnClickListener {

            val buttonclick = DatabaseMode.databaseMode!!.getClickMode()
            val color = DatabaseMode.databaseMode!!.getModeColor()
            val nightColor1 = Color.parseColor(colorsStatusbar[0])
            val nightColor2 = Color.parseColor(colorsStatusbar[1])
            if (buttonclick == false) {

                button!!.setImageResource(R.drawable.ic_sun)
                nightMode(nightColor1, nightColor2)
                binding.rv.setBackgroundColor(Color.parseColor(colorsStatusbar[1]))
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                DatabaseMode.databaseMode!!.saveMode("night")
                DatabaseMode.databaseMode!!.saveClickMode(true)
                binding.drawMenu.setImageResource(R.drawable.ic_menu_white)
                loadAdapter()

            } else {
                button!!.setImageResource(R.drawable.ic_moon)
                binding.customActionbar.setBackgroundColor(Color.parseColor(color))
                backheader!!.setBackgroundColor(Color.parseColor(color))
                activity?.window?.navigationBarColor = Color.parseColor(color)
                activity?.window?.statusBarColor = Color.parseColor(color)
                binding.navViewHome.setBackgroundColor(Color.WHITE)
                binding.rv.setBackgroundColor(Color.WHITE)
                binding.drawMenu.setImageResource(R.drawable.ic_menu)
                //changeIcon(true)
                //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                DatabaseMode.databaseMode!!.saveMode("day")
                DatabaseMode.databaseMode!!.saveClickMode(false)
                loadAdapter()

            }


        }


    }

    private fun loadAdapter() {
        adapter = AdapterItem()
        binding.rv.adapter = adapter
        adapter!!.listener = this
        adapter!!.list = list
        adapter!!.notifyDataSetChanged()
    }

    private fun nightMode(nightColor1: Int, nightColor2: Int) {
        backheader?.setBackgroundColor(nightColor2)
        activity?.window?.navigationBarColor = nightColor2
        activity?.window?.statusBarColor = nightColor2
        binding.navViewHome.setBackgroundColor(nightColor1)
        binding.navViewHome.menu.findItem(R.id.exit).setIcon(R.drawable.ic_settings)
        binding.customActionbar.setBackgroundColor(nightColor2)
    }

    private fun changeIcon(b: Boolean) {
        val menuIcon = binding.navViewHome.menu
        if (b) {
            menuIcon.findItem(R.id.about_book).setIcon(R.drawable.ic_about_writer)
            menuIcon.findItem(R.id.settings_menu).setIcon(R.drawable.ic_settings)
            menuIcon.findItem(R.id.think).setIcon(R.drawable.ic_think)
            menuIcon.findItem(R.id.about_prog).setIcon(R.drawable.ic_about_prog)
            menuIcon.findItem(R.id.exit).setIcon(R.drawable.ic_exit)
        } else {
            menuIcon.findItem(R.id.about_book).setIcon(R.drawable.ic_about_writer_white)
            menuIcon.findItem(R.id.settings_menu).setIcon(R.drawable.ic_settings_white)
            menuIcon.findItem(R.id.think).setIcon(R.drawable.ic_think_white)
            menuIcon.findItem(R.id.about_prog).setIcon(R.drawable.ic_about_prog_white)
            menuIcon.findItem(R.id.exit).setIcon(R.drawable.ic_exit_white)
        }
    }


    private fun loadMode() {
        val modeTheme = DatabaseMode.databaseMode!!.getMode()
        val color = Color.parseColor(DatabaseMode.databaseMode!!.getModeColor())


        if (modeTheme.equals("night")) {
            backheader!!.setBackgroundColor(Color.parseColor(colorsStatusbar[1]))
            activity?.window!!.navigationBarColor = Color.parseColor(colorsStatusbar[1])
            activity?.window!!.statusBarColor = Color.parseColor(colorsStatusbar[1])
            binding.customActionbar.setBackgroundColor(Color.parseColor(colorsStatusbar[1]))
            binding.rv.setBackgroundColor(Color.parseColor(colorsStatusbar[1]))
            button!!.setImageResource(R.drawable.ic_sun)
            binding.navViewHome.setBackgroundColor(Color.parseColor(colorsStatusbar[0]))
            binding.drawMenu.setImageResource(R.drawable.ic_menu_white)
            // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            loadAdapter()

        } else {
            button!!.setImageResource(R.drawable.ic_moon)
            backheader!!.setBackgroundColor(color)
            activity?.window?.navigationBarColor = color
            activity?.window?.statusBarColor = color
            binding.navViewHome.setBackgroundColor(Color.WHITE)
            binding.customActionbar.setBackgroundColor(color)
            binding.rv.setBackgroundColor(Color.WHITE)
            binding.drawMenu.setImageResource(R.drawable.ic_menu)
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            loadAdapter()
        }
    }

    private fun gendataAssets(): ItemDataClass? {
        var json = ""
        val lang = DatabaseMode.databaseMode!!.getModeLang()
        var jsonFileName: String? = null
        if (lang.equals("en")) {
            jsonFileName = "hikoyalar.json"
        } else {
            jsonFileName = "hikoyalar_krill.json"
        }
        val stream = requireContext().assets.open(jsonFileName)
        val size = stream.available()
        val buffer = ByteArray(size)
        stream.read(buffer)
        stream.close()
        json = String(buffer)
        val gson = Gson()
        val tests = gson.fromJson<ItemDataClass>(json, ItemDataClass::class.java)

        return tests
    }

    override fun onItemClick(list: ItemDataClass, position: Int) {

        findNavController().navigate(
            R.id.secondFragment,
            bundleOf("position" to position, "list" to list)
        )
    }

    @SuppressLint("WrongConstant")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about_book -> {
                Toast.makeText(requireContext(), "WRITER", Toast.LENGTH_SHORT).show()
                binding.drawer.closeDrawer(Gravity.START)
                findNavController().navigate(R.id.blankFragment)
            }
            R.id.settings_menu -> {
                Toast.makeText(requireContext(), "SETTINGS", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.settings2)
            }
            R.id.think -> {
                Toast.makeText(requireContext(), "SETTINGS", Toast.LENGTH_SHORT).show()


            }
            R.id.about_prog -> {
                Toast.makeText(requireContext(), "ABOUT", Toast.LENGTH_SHORT).show()
            }
            R.id.exit -> {
                Toast.makeText(requireContext(), "EXIT", Toast.LENGTH_SHORT).show()
            }
        }

        return true
    }

    private fun makeToast(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
    }

}


