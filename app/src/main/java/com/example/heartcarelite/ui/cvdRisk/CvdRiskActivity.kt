package com.example.heartcarelite.ui.cvdRisk

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.heartcarelite.R
import com.example.heartcarelite.databinding.ActivityCvdRiskBinding
import com.example.heartcarelite.databinding.ActivityLandingBinding
import com.example.heartcarelite.model.CVDRiskChart
import com.example.heartcarelite.model.User
import com.example.heartcarelite.model.UserInfo
import com.example.heartcarelite.ui.cvdRisk.saveRecord.SaveRecordActivity
import com.example.heartcarelite.ui.landingPage.LandingActivity
import com.example.heartcarelite.ui.userList.UserListActivity
import com.example.heartcarelite.ui.userList.UserListViewModel
import com.example.heartcarelite.utils.CalendarHelper
import com.example.heartcarelite.utils.HelperFunction.afterTextChanged
import com.example.heartcarelite.utils.HelperFunction.toAgeDob
import com.example.heartcarelite.utils.toAge
import com.example.heartcarelite.utils.toDate
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CvdRiskActivity : AppCompatActivity() {

    private var inputUserAge: String = ""
    var alertDialog: AlertDialog? = null
    private lateinit var customAlertDialogView : View
    private lateinit var binding: ActivityCvdRiskBinding
    private val cvdRiskViewModel: CvdRiskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCvdRiskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Assess risk"


        binding.bNextActivity.isEnabled = false
        binding.bNextActivity.isClickable = false

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        binding.labelCardiovascularEvent.setOnClickListener {
            customAlertDialogView = LayoutInflater.from(this)
                .inflate(R.layout.card, null, false)
            creteMaterialDialog()
            alertDialog?.show()
        }

        binding.heightPrefix.afterTextChanged {
            val postfix = binding.heightPrefix.text.toString()
            cvdRiskViewModel.inputFeet.set("")
            cvdRiskViewModel.inputInch.set("")
            cvdRiskViewModel.inputCm.set("")
            if(postfix == "cm"){
                binding.heightContainerInFt.isVisible = false
                binding.heightFt.isVisible = false
                binding.heightContainer.isVisible = false
                binding.heightCm.isVisible = false
                binding.heightContainerInCm.isVisible =true
                binding.heightCm.isVisible = true
                cvdRiskViewModel.inputFeet.set("")
                cvdRiskViewModel.inputInch.set("")
            }else{
                binding.heightContainerInFt.isVisible = true
                binding.heightFt.isVisible = true
                binding.heightContainer.isVisible = true
                binding.heightCm.isVisible = true
                binding.heightContainerInCm.isVisible =false
                binding.heightCm.isVisible = false
                cvdRiskViewModel.inputFeet.set("")
                cvdRiskViewModel.inputInch.set("")
                cvdRiskViewModel.inputCm.set("")
            }

        }
        binding.heightCm.afterTextChanged {

            val centimeter = binding.heightCm.text.toString()

            if(centimeter.length >= 3){
                val feet = 0.0328083989501 * centimeter.toDouble();//centimeter.toDouble() / 30.48
                val inch = 0.3937007874029 * centimeter.toDouble();
                cvdRiskViewModel.inputFeet.set(feet.toInt().toString())
                cvdRiskViewModel.inputInch.set(inch.toInt().toString())
                Log.e("CM","${feet.toInt()} ft ${inch.toInt()} in")
            }

        }

        binding.inputDob.afterTextChanged {
            inputUserAge = if(cvdRiskViewModel.selectedDob.get().toAge() < 44) 44.toString() else cvdRiskViewModel.selectedDob.get().toAge().toString()
        }
        binding.inputAge.afterTextChanged {
            inputUserAge = if(cvdRiskViewModel.age.get()!!.toInt() < 44) 44.toString() else cvdRiskViewModel.age.get().toString()
        }

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

        binding.bPredict.setOnClickListener {

            binding.bNextActivity.isEnabled = true
            binding.bNextActivity.isClickable = true
                    binding.riskCardView.visibility = View.VISIBLE
                if(binding.cholesterol.text!!.isNotEmpty()){
                        predictRiskUsingCholesterol()
                }else{
                    predictRiskUsingBMI()
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
                Log.e("empty","put some value")
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

    private fun predictRiskUsingCholesterol(
        regionCode:String = "Oceania",
        isDiabetes:String = cvdRiskViewModel.inputUserDiabetesInt.value.toString(),
        gender:String = cvdRiskViewModel.inputUserSexInt.value.toString(),
        isSmoker:String = cvdRiskViewModel.inputUserTobaccoUserInt.value.toString(),
        age:String = if(binding.chipDob.isChecked) inputUserAge else cvdRiskViewModel.age.get().toString(),
        systolic:String = cvdRiskViewModel.sys.get().toString(),
        cholesterol:String = cvdRiskViewModel.inputTotalCholesterol.value.toString(),
        cholesterolUnit:String = "mmol_L",
        BMI:String = ".",
        BMIUnit:String = "kg/m^2",
    ){
         val fetch = cvdRiskViewModel.computeRisk(
             regionCode,
             isDiabetes,
             gender,
             isSmoker,
             age,
             systolic,
             cholesterol,
             cholesterolUnit,
             BMI,
             BMIUnit,
        )
        if(fetch.isEmpty()){
            Log.e("empty","list is empty: Cholesterol")
        }else{
            val hs = fetch.last()
            Log.e("fetch", "ID: ${hs.id}")
            cvdRiskViewModel.inputCvdScore.value = hs.hsValue
            binding.textview.text = cvdRiskViewModel.inputCvdScore.value + "%"
            when(hs.riskLevel){
                "Green" ->{
                    binding.textview.setBackgroundResource(R.drawable.bg_green_rounded)
                    cvdRiskViewModel.inputRiskMessage.value = "Low risk"
                    binding.titleWarningText.text = "low risk of heart attack or stroke"
                }
                "Yellow" ->{
                    binding.textview.setBackgroundResource(R.drawable.bg_yellow_rounded)
                    cvdRiskViewModel.inputRiskMessage.value = "Moderate risk"
                    binding.titleWarningText.text = "Moderate risk of heart attack or stroke"
                }
                "Orange" ->{
                    binding.textview.setBackgroundResource(R.drawable.bg_red_rounded)
                    cvdRiskViewModel.inputRiskMessage.value = "High risk"
                    binding.titleWarningText.text = "High risk of heart attack or stroke"
                }
                "Red" ->{
                    binding.textview.setBackgroundResource(R.drawable.bg_red_rounded)
                    cvdRiskViewModel.inputRiskMessage.value = "Very high risk"
                    binding.titleWarningText.text = "10-year risk of heart attack or stroke"
                }
                "Deep_Red" ->{
                    binding.textview.setBackgroundResource(R.drawable.bg_red_rounded)
                    cvdRiskViewModel.inputRiskMessage.value = "Extremely high risk"
                    binding.titleWarningText.text = "10-year risk of heart attack or stroke"
                }
            }
        }
    }

    private fun predictRiskUsingBMI(
        regionCode:String = "Oceania",
        isDiabetes:String = cvdRiskViewModel.inputUserDiabetesInt.value.toString(),
        gender:String = cvdRiskViewModel.inputUserSexInt.value.toString(),
        isSmoker:String = cvdRiskViewModel.inputUserTobaccoUserInt.value.toString(),
        age:String = if(binding.chipDob.isChecked) inputUserAge else cvdRiskViewModel.age.get().toString(),
        systolic:String = cvdRiskViewModel.sys.get().toString(),
        cholesterol:String = ".",
        cholesterolUnit:String = "mmol_L",
        BMI:String = cvdRiskViewModel.inputUserBMI.value.toString(),
        BMIUnit:String = "kg/m^2",
    ){
        val fetch = cvdRiskViewModel.computeRisk(
            regionCode,
            isDiabetes,
            gender,
            isSmoker,
            age,
            systolic,
            cholesterol,
            cholesterolUnit,
            BMI,
            BMIUnit,
        )
        if(fetch.isEmpty()){
            Log.e("empty","list is empty : BMI")
        }else{
            val hs = fetch.last()
            Log.e("fetch", "ID: ${hs.id}")
            cvdRiskViewModel.inputCvdScore.value = hs.hsValue + "%"
            binding.textview.text = cvdRiskViewModel.inputCvdScore.value  + "%"
            when(hs.riskLevel){
                "Green" ->{
                    binding.textview.setBackgroundResource(R.drawable.bg_grey_rounded)
                    cvdRiskViewModel.inputRiskMessage.value = "Low risk"
                }
                "Yellow" ->{
                    binding.textview.setBackgroundResource(R.drawable.bg_green_rounded)
                    cvdRiskViewModel.inputRiskMessage.value = "Moderate risk"
                }
                "Orange" ->{
                    binding.textview.setBackgroundResource(R.drawable.bg_orange_rounded)
                    cvdRiskViewModel.inputRiskMessage.value = "High risk"
                }
                "Red" ->{
                    binding.textview.setBackgroundResource(R.drawable.bg_red_rounded)
                    cvdRiskViewModel.inputRiskMessage.value = "Very high risk"
                }
                "Deep_Red" ->{
                    binding.textview.setBackgroundResource(R.drawable.bg_deep_red_rounded)
                    cvdRiskViewModel.inputRiskMessage.value = "Extremely high risk"
                }
            }
        }

    }
    private fun creteMaterialDialog(){
        val builder = MaterialAlertDialogBuilder(this,R.style.AlertDialogTheme)
        with(builder) {
            setView(customAlertDialogView)
            setPositiveButton("OK") { _, _ -> }

        }
        alertDialog = builder.create()

    }
    private fun displayMessage(message : String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}




