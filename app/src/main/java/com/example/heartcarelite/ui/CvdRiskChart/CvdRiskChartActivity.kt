package com.example.heartcarelite.ui.CvdRiskChart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.example.heartcarelite.R
import com.example.heartcarelite.databinding.ActivityCvdRiskChartBinding
import com.example.heartcarelite.databinding.ActivityMainBinding
import com.example.heartcarelite.utils.HelperFunction.afterTextChanged

class CvdRiskChartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCvdRiskChartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCvdRiskChartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "CVD risk tables"

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        val ss:String = intent.getStringExtra("country").toString()


        val gender = resources.getStringArray(R.array.gender)
        val genderAdapter = ArrayAdapter(this, R.layout.dropdown_item, gender)
        binding.TextFieldGender.setAdapter(genderAdapter)

        val smoker = resources.getStringArray(R.array.smoker)
        val smokerAdapter = ArrayAdapter(this, R.layout.dropdown_item, smoker)
        binding.TextFieldSmoker.setAdapter(smokerAdapter)

        val cholesterolOrBMI = resources.getStringArray(R.array.BMI_or_Cholesterol)
        val cholesterolOrBMIAdapter = ArrayAdapter(this, R.layout.dropdown_item, cholesterolOrBMI)
        binding.TextFieldCholesterol.setAdapter(cholesterolOrBMIAdapter)

        val diabetic = resources.getStringArray(R.array.diabetic)
        val diabeticAdapter = ArrayAdapter(this, R.layout.dropdown_item, diabetic)
        binding.TextFieldDiabetic.setAdapter(diabeticAdapter)

        binding.TextFieldCholesterol.afterTextChanged {
            val gender = binding.TextFieldGender.text.toString()
            val smoker = binding.TextFieldSmoker.text.toString()
            val cholesterolOrBMI = binding.TextFieldCholesterol.text.toString()
            if(cholesterolOrBMI == "BMI" && gender == "Male" && smoker == "Non-smoker"){
                val imgResId = R.drawable.oc_bmi_male_non_smoker
                val resId = imgResId
                binding.chart.setImageResource(resId)
                val imgResIdBottomCardBMI = R.drawable.legend_bottom_bmi
                val resIdBMI = imgResIdBottomCardBMI
                binding.bottomCard.setImageResource(resIdBMI)
            }else if(cholesterolOrBMI == "BMI" && gender == "Male" && smoker == "Smoker"){
                val imgResId = R.drawable.oc_bmi_male_smoker
                val resId = imgResId
                binding.chart.setImageResource(resId)
                val imgResIdBottomCardBMI = R.drawable.legend_bottom_bmi
                val resIdBMI = imgResIdBottomCardBMI
                binding.bottomCard.setImageResource(resIdBMI)
            }else if(cholesterolOrBMI == "BMI" && gender == "Female" && smoker == "Non-smoker"){
                val imgResId = R.drawable.oc_bmi_female_non_smoker
                val resId = imgResId
                binding.chart.setImageResource(resId)
                val imgResIdBottomCardBMI = R.drawable.legend_bottom_bmi
                val resIdBMI = imgResIdBottomCardBMI
                binding.bottomCard.setImageResource(resIdBMI)
            }else if(cholesterolOrBMI == "BMI" && gender == "Female" && smoker == "Smoker"){
                val imgResId = R.drawable.oc_bmi_female_smoker
                val resId = imgResId
                binding.chart.setImageResource(resId)
                val imgResIdBottomCardBMI = R.drawable.legend_bottom_bmi
                val resIdBMI = imgResIdBottomCardBMI
                binding.bottomCard.setImageResource(resIdBMI)
            }else if(cholesterolOrBMI == "Cholesterol"){
                binding.TextFieldDiabetic.isVisible = true
                binding.diabeticContainer.isVisible = true
                binding.TextFieldDiabetic.afterTextChanged {
                    val gender = binding.TextFieldGender.text.toString()
                    val smoker = binding.TextFieldSmoker.text.toString()
                    val cholesterolOrBMI = binding.TextFieldCholesterol.text.toString()
                    val diabetic = binding.TextFieldDiabetic.text.toString()
                    if(diabetic == "Diabetic" && gender == "Male" && smoker == "Non-smoker"){
                        val imgResId = R.drawable.oc_cl_male_non_smoker_with_diabetes
                        val resId = imgResId
                        binding.chart.setImageResource(resId)
                        val imgResIdBottomCardBMI = R.drawable.legend_bottom_cholesterol
                        val resIdBMI = imgResIdBottomCardBMI
                        binding.bottomCard.setImageResource(resIdBMI)
                    }else if(diabetic == "Diabetic" && gender == "Male" && smoker == "Smoker"){
                        val imgResId = R.drawable.oc_cl_male_smoker_with_diabetes
                        val resId = imgResId
                        binding.chart.setImageResource(resId)
                        val imgResIdBottomCardBMI = R.drawable.legend_bottom_cholesterol
                        val resIdBMI = imgResIdBottomCardBMI
                        binding.bottomCard.setImageResource(resIdBMI)
                    }else if(diabetic == "Non-diabetic" && gender == "Male" && smoker == "Non-smoker"){
                        val imgResId = R.drawable.oc_cl_male_non_smoker_without_diabetes
                        val resId = imgResId
                        binding.chart.setImageResource(resId)
                        val imgResIdBottomCardBMI = R.drawable.legend_bottom_cholesterol
                        val resIdBMI = imgResIdBottomCardBMI
                        binding.bottomCard.setImageResource(resIdBMI)
                    }else if(diabetic == "Non-diabetic" && gender == "Male" && smoker == "Smoker"){
                        val imgResId = R.drawable.oc_cl_male_smoker_without_diabetes
                        val resId = imgResId
                        binding.chart.setImageResource(resId)
                        val imgResIdBottomCardBMI = R.drawable.legend_bottom_cholesterol
                        val resIdBMI = imgResIdBottomCardBMI
                        binding.bottomCard.setImageResource(resIdBMI)
                    }else if(diabetic == "Diabetic" && gender == "Female" && smoker == "Non-smoker"){
                        val imgResId = R.drawable.oc_cl_female_non_smoker_with_diabetes
                        val resId = imgResId
                        binding.chart.setImageResource(resId)
                        val imgResIdBottomCardBMI = R.drawable.legend_bottom_cholesterol
                        val resIdBMI = imgResIdBottomCardBMI
                        binding.bottomCard.setImageResource(resIdBMI)
                    }else if(diabetic == "Diabetic" && gender == "Female" && smoker == "Smoker"){
                        val imgResId = R.drawable.oc_cl_female_smoker_with_diabetes
                        val resId = imgResId
                        binding.chart.setImageResource(resId)
                        val imgResIdBottomCardBMI = R.drawable.legend_bottom_cholesterol
                        val resIdBMI = imgResIdBottomCardBMI
                        binding.bottomCard.setImageResource(resIdBMI)
                    }else if(diabetic == "Non-diabetic" && gender == "Female" && smoker == "Non-smoker"){
                        val imgResId = R.drawable.oc_cl_female_non_smoker_without_diabetes
                        val resId = imgResId
                        binding.chart.setImageResource(resId)
                        val imgResIdBottomCardBMI = R.drawable.legend_bottom_cholesterol
                        val resIdBMI = imgResIdBottomCardBMI
                        binding.bottomCard.setImageResource(resIdBMI)
                    }else if(diabetic == "Non-diabetic" && gender == "Female" && smoker == "Smoker"){
                        val imgResId = R.drawable.oc_cl_female_smoker_without_diabetes
                        val resId = imgResId
                        binding.chart.setImageResource(resId)
                        val imgResIdBottomCardBMI = R.drawable.legend_bottom_cholesterol
                        val resIdBMI = imgResIdBottomCardBMI
                        binding.bottomCard.setImageResource(resIdBMI)
                    }
                }
            }else if(cholesterolOrBMI == "BMI"){
                binding.TextFieldDiabetic.isVisible = false
                binding.diabeticContainer.isVisible = false
            }
        }


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }
}