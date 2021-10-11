package com.example.heartcarelite.ui.main

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.heartcarelite.R
import com.example.heartcarelite.databinding.ActivityMainBinding
import com.example.heartcarelite.ui.cvdRisk.saveRecord.SaveRecordActivity
import com.example.heartcarelite.ui.landingPage.LandingActivity
import com.example.heartcarelite.utils.HelperFunction.afterTextChanged
import android.content.ClipData.Item
import android.util.Log
import android.widget.AdapterView.OnItemClickListener


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var countryselected:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bNextActivity.isEnabled = false
        binding.bNextActivity.isClickable  = false

        val country = resources.getStringArray(R.array.country)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, country)
        binding.TextFieldCountry.setAdapter(arrayAdapter)
        val index = binding.TextFieldCountry.setOnItemClickListener { adapterView, view, pos, rowId ->
            countryselected = adapterView.getItemIdAtPosition(pos).toString()
        }


        binding.TextFieldCountry.afterTextChanged {

            countryselected = binding.TextFieldCountry.text.toString()
            if (countryselected != "Select Country"){
                binding.bNextActivity.isEnabled = true
                binding.bNextActivity.isClickable  = true
            }
        }

        binding.bNextActivity.setOnClickListener {
            val i = Intent(this@MainActivity, LandingActivity::class.java)
            i.putExtra("country",countryselected)
            startActivity(i)
        }


    }
}