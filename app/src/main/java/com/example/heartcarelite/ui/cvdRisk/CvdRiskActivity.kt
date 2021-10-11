package com.example.heartcarelite.ui.cvdRisk

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.heartcarelite.R
import com.example.heartcarelite.databinding.ActivityCvdRiskBinding
import com.example.heartcarelite.databinding.ActivityLandingBinding
import com.example.heartcarelite.model.User
import com.example.heartcarelite.model.UserInfo
import com.example.heartcarelite.ui.cvdRisk.saveRecord.SaveRecordActivity
import com.example.heartcarelite.ui.landingPage.LandingActivity
import com.example.heartcarelite.ui.userList.UserListViewModel
import com.example.heartcarelite.utils.CalendarHelper
import com.example.heartcarelite.utils.HelperFunction.afterTextChanged
import com.example.heartcarelite.utils.HelperFunction.toAgeDob
import com.example.heartcarelite.utils.toAge
import com.example.heartcarelite.utils.toDate
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CvdRiskActivity : AppCompatActivity() {

    private var inputScreeningDate: String = ""
    private var inputUserDOB: String = ""
    private var inputUserAge: String = ""
    private var inputUserSex: String = ""
    private var inputUserDiabetes: String = ""
    private var inputUserTobaccoUser: String = ""
    private var inputCardiovascularEvent: String = ""
    private var inputUserBloodPressure: String = ""
    private var inputUserHeight: String = ""
    private var inputUserWeight: String = ""
    private var inputUserBMI: String = ""
    private var inputTotalCholesterol: String = ""
    private var inputCvdScore: String = ""


    private lateinit var binding: ActivityCvdRiskBinding
    private val cvdRiskViewModel: CvdRiskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCvdRiskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Assess risk"

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        binding.lifecycleOwner = this
        binding.myViewModel = cvdRiskViewModel
        checkBMI()

        binding.bNextActivity.setOnClickListener {

            if (true) {
                val i = Intent(this@CvdRiskActivity, SaveRecordActivity::class.java)
                i.putExtra(
                    "userData", UserInfo(
                        screeningDate = cvdRiskViewModel.inputScreeningDate.get().toString(),
                        userDOB = cvdRiskViewModel.selectedDob.get().toString(),
                        userAge = if(binding.chipDob.isChecked) cvdRiskViewModel.selectedDob.get().toAge().toString() else cvdRiskViewModel.age.get().toString(),
                        userSex = cvdRiskViewModel.inputUserSex.value.toString(),
                        userDiabetes = cvdRiskViewModel.inputUserDiabetes.value.toString(),
                        userTobaccoUser = cvdRiskViewModel.inputUserTobaccoUser.value.toString(),
                        cardiovascularEvent = cvdRiskViewModel.inputCardiovascularEvent.value.toString(),
                        userBloodPressure = "${cvdRiskViewModel.inputUserBloodPressure.value.toString()} mmHg",
                        userHeight = "${cvdRiskViewModel.inputFeet.get().toString()} ft ${cvdRiskViewModel.inputInch.get().toString()} in",
                        userWeight = "${cvdRiskViewModel.inputUserWeight.get().toString()} ${cvdRiskViewModel.inputUserWeightPostfix.get().toString()}",
                        userBMI = cvdRiskViewModel.inputUserBMI.value.toString(),
                        totalCholesterol = cvdRiskViewModel.inputTotalCholesterol.value.toString(),
                        cvdScore = cvdRiskViewModel.inputCvdScore.value.toString(),
                    )
                )
                startActivity(i)
            }

        }




    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }

    fun checkBMI(){

        binding.weight.afterTextChanged {
            if (binding.weight.text.toString().length < 1){
                cvdRiskViewModel.inputUserBMI.value = ""
                binding.labelBMI.setBackgroundResource(R.color.white);
                binding.labelBMIPostfix.setBackgroundResource(R.color.white);
            }
            try {
                val weight = binding.weight.text.toString().toDouble()
                val heightInFeet = binding.heightFt.text.toString().toDouble()
                val heightInInch = binding.heightIn.text.toString().toDouble()
                val centimeterConversion = heightInFeet * 30.48 + heightInInch * 2.54
                cvdRiskViewModel.compute(weight,centimeterConversion)
                BMIBackground()
            }catch (e:Exception){
                Log.d("empty","put some value")
            }
        }
    }
    fun BMIBackground() {
        if (cvdRiskViewModel.inputUserBMI.value!!.toFloat() <= 18.5) {
            binding.labelBMI.setBackgroundResource(R.color.bmi_grey);
            binding.labelBMIPostfix.setBackgroundResource(R.color.bmi_grey);
        } else if (cvdRiskViewModel.inputUserBMI.value!!.toFloat() in 18.6..24.9) {
            binding.labelBMI.setBackgroundResource(R.color.bmi_green);
            binding.labelBMIPostfix.setBackgroundResource(R.color.bmi_green);
        } else if(cvdRiskViewModel.inputUserBMI.value!!.toFloat() in 25.0..29.9){
            binding.labelBMI.setBackgroundResource(R.color.bmi_yellow);
            binding.labelBMIPostfix.setBackgroundResource(R.color.bmi_yellow);
        }  else if(cvdRiskViewModel.inputUserBMI.value!!.toFloat() > 30.0){
            binding.labelBMI.setBackgroundResource(R.color.bmi_red);
            binding.labelBMIPostfix.setBackgroundResource(R.color.bmi_red);
        }
    }

}




