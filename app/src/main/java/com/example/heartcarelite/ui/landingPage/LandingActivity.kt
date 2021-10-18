package com.example.heartcarelite.ui.landingPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.heartcarelite.R
import com.example.heartcarelite.databinding.ActivityLandingBinding
import com.example.heartcarelite.databinding.ActivityMainBinding
import com.example.heartcarelite.ui.CvdRiskChart.CvdRiskChartActivity
import com.example.heartcarelite.ui.cvdRisk.CvdRiskActivity
import com.example.heartcarelite.ui.userList.UserListActivity

class LandingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val country = resources.getStringArray(R.array.country)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, country)
        binding.TextFieldCountry.setAdapter(arrayAdapter)


        binding.cardView.setOnClickListener {
            val i = Intent(this@LandingActivity, CvdRiskActivity::class.java)
            startActivity(i)
        }
        binding.cardView2.setOnClickListener {
            val i = Intent(this@LandingActivity, UserListActivity::class.java)
            startActivity(i)
        }
        binding.cardView1.setOnClickListener {
            val i = Intent(this@LandingActivity, CvdRiskChartActivity::class.java)
            startActivity(i)
        }

        val ss:String = intent.getStringExtra("country").toString()
        binding.TextFieldCountry.setText(arrayAdapter.getItem(ss.toInt()),false)
    }

    override fun onBackPressed() {
        finishAffinity()
        super.onBackPressed()
    }

}