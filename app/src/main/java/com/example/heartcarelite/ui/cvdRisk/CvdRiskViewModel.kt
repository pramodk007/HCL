package com.example.heartcarelite.ui.cvdRisk

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableLong
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heartcarelite.R
import com.example.heartcarelite.model.CVDRiskChart
import com.example.heartcarelite.model.UserInfo
import com.example.heartcarelite.repository.cvdRiskRepo.CvdRiskRepository
import com.example.heartcarelite.utils.Event
import com.example.heartcarelite.utils.HelperFunction.asLiveData
import com.example.heartcarelite.utils.toAge
import com.example.heartcarelite.utils.toDate
import com.example.heartcarelite.utils.toTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Math.round
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*
import javax.inject.Inject
import kotlin.math.pow

@HiltViewModel
class CvdRiskViewModel @Inject constructor(private val cvdRiskRepository: CvdRiskRepository) : ViewModel(), Observable {

    @Bindable
    val inputScreeningDate = ObservableLong(System.currentTimeMillis())
    @Bindable
    val maximumScreeningDate = ObservableLong(System.currentTimeMillis())

    @Bindable
    val selectedDob = ObservableLong(0)

    @Bindable
    val maximumDob = ObservableLong(System.currentTimeMillis())

    @Bindable
    val age = ObservableField("")

    @Bindable
    private val _showDOB = MutableLiveData(true)
    val showDob = _showDOB.asLiveData()

    @Bindable
    val inputUserSex = MutableLiveData<String>()
    val inputUserSexInt = MutableLiveData<Int>()

    @Bindable
    val inputUserDiabetes = MutableLiveData<String>()
    val inputUserDiabetesInt = MutableLiveData<Int>()

    @Bindable
    val inputUserTobaccoUser = MutableLiveData<String>()
    val inputUserTobaccoUserInt = MutableLiveData<Int>()

    @Bindable
    val inputCardiovascularEvent = MutableLiveData<String>()
    val inputCardiovascularEventInt = MutableLiveData<Int>()

    @Bindable
    val inputUserBloodPressure = MutableLiveData<String>()

    @Bindable
    val inputFeet= ObservableField("")

    @Bindable
    val inputInch = ObservableField("")

    @Bindable
    val inputCm = ObservableField("")

    @Bindable
    val inputHeightPostfix = ObservableField("ft/in")

    @Bindable
    val inputUserWeight = ObservableField("")

    @Bindable
    val inputUserWeightPostfix = ObservableField("kg")

    @Bindable
    var inputUserBMI = MutableLiveData<String>("")


    @Bindable
    val inputTotalCholesterol = MutableLiveData<String>()

    @Bindable
    val inputUserCholesterolPostfix = MutableLiveData<String>("mg/dL")

    @Bindable
    val inputRiskMessage = MutableLiveData<String?>()

    @Bindable
    val inputCvdScore = MutableLiveData<String?>()

    @Bindable
    val sys = ObservableField("")
    @Bindable
    val dia = ObservableField("")
    @Bindable
    val sysError = object : ObservableField<Int?>(sys, dia) {
        override fun get(): Int? {
            val sys = sys.get()?.toIntOrNull() ?: return null
            val dia = dia.get()?.toIntOrNull() ?: return null
            inputUserBloodPressure.value = "${sys}/${dia}"
            return if (sys < dia) R.string.text_sys_not_greater else null
        }
    }

    fun computeBmi(weight: String, height: String) {
            if (weight.trim().isNotBlank()){
                var computedBmi: Double
                val numerator = weight.toFloat()
                val denominator = (height.toFloat() * 0.01).pow(2.0)
                computedBmi = numerator / denominator
                computedBmi = round(computedBmi * 10.0) / 10.0
                inputUserBMI.value = computedBmi.toString()
            }else{
                inputUserBMI.value = "BMI"
            }
    }
    fun compute(weight: Double?, height: Double?){

        if (weight.toString().trim().isNotBlank()){
            val h = height!! /100
            val w = weight!!
            val res = w/(h*h)
            val result= "%.2f".format(res)
            inputUserBMI.value = result
        }

    }


    fun setDOBType(id: Int) {
        val dobTypeDefault = id == R.id.chip_dob
        _showDOB.postValue(dobTypeDefault)
        age.set("")
        //selectedDob.set(0)
        //valid.set(false)
    }

    fun setSex(id: Int) {
        when (id) {
            R.id.chip_male -> {
                inputUserSex.value = "M"
                inputUserSexInt.value = 1
            }
            R.id.chip_female -> {
                inputUserSex.value = "F"
                inputUserSexInt.value = 0
            }

        }
    }

    fun setDiabetes(id: Int) {
        when (id) {
            R.id.chip_diabetes_yes -> {
                inputUserDiabetes.value = "diabetes yes"
                inputUserDiabetesInt.value = 1
            }
            R.id.chip_diabetes_no -> {
                inputUserDiabetes.value = "diabetes no"
                inputUserDiabetesInt.value = 0
            }
        }
    }

    fun setTobacco(id: Int) {
        when (id) {
            R.id.chip_tobacco_yes -> {
                inputUserTobaccoUser.value = "Tobacco User"
                inputUserTobaccoUserInt.value = 1
            }
            R.id.chip_tobacco_no -> {
                inputUserTobaccoUser.value = "not a Tobacco User"
                inputUserTobaccoUserInt.value = 0
            }

        }
    }

    fun setCardiovascularEvent(id: Int) {
        when (id) {
            R.id.chip_cardiovascularEvent_yes -> {
                inputCardiovascularEvent.value = "with a history of Cardiovascular Event"
                inputCardiovascularEventInt.value = 1
            }
            R.id.chip_cardiovascularEvent_no -> {
                inputCardiovascularEvent.value = "no history of Cardiovascular Event"
                inputCardiovascularEventInt.value = 0
            }

        }
    }

    fun computeRisk(
        regionCode:String,
        isDiabetes:String,
        gender:String,
        isSmoker:String,
        age:String,
        systolic:String,
        cholesterol:String,
        cholesterolUnit:String,
        BMI:String,
        BMIUnit:String,
    ) :List<CVDRiskChart>{
         return cvdRiskRepository.getCvdDataDetails(
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
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}