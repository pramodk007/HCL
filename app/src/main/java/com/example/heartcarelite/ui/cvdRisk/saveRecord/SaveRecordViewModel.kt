package com.example.heartcarelite.ui.cvdRisk.saveRecord

import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heartcarelite.model.User
import com.example.heartcarelite.model.UserInfo
import com.example.heartcarelite.repository.cvdRiskRepo.CvdRiskRepository
import com.example.heartcarelite.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveRecordViewModel @Inject constructor(private val cvdRiskRepository: CvdRiskRepository):ViewModel(), Observable {

    private var isUpdateOrDelete = false

    private lateinit var userToUpdateOrDelete : User

    @Bindable
    val saveOrUpdateBT = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteBT = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message : LiveData<Event<String>>
        get() = statusMessage


    @Bindable
    val inputPatientId = MutableLiveData<String?>()

    @Bindable
    val inputFirstName = MutableLiveData<String?>()

    @Bindable
    val inputMiddleName = MutableLiveData<String?>()

    @Bindable
    val inputLastName = MutableLiveData<String?>()

    @Bindable
    var inputAge = MutableLiveData<String?>()

    var inputDOB = MutableLiveData<String?>()

    @Bindable
    val inputSex = MutableLiveData<String?>()

    @Bindable
    val inputPhoneNumber = MutableLiveData<String?>()

    @Bindable
    val inputAddressLineOne = MutableLiveData<String?>()

    @Bindable
    val inputAddressLineTwo = MutableLiveData<String?>()

    @Bindable
    val inputAddressLineThree = MutableLiveData<String?>()

    @Bindable
    val inputCountry = MutableLiveData<String?>()

    @Bindable
    val inputPinCode = MutableLiveData<String?>()

    var screeningDate = MutableLiveData<String?>()
    var userDiabetes = MutableLiveData<String?>()
    var userTobaccoUser = MutableLiveData<String?>()
    var cardiovascularEvent = MutableLiveData<String?>()
    var userBloodPressure = MutableLiveData<String?>()
    var userHeight = MutableLiveData<String?>()
    var userWeight = MutableLiveData<String?>()
    var userBMI = MutableLiveData<String?>()
    var totalCholesterol = MutableLiveData<String?>()
    var cvdScore = MutableLiveData<String?>()



    init {
        saveOrUpdateBT.value = "Save"
        clearAllOrDeleteBT.value = "Clear all"
    }

    fun saveOrUpdate(){
        if(inputPatientId.value == null){
            statusMessage.value = Event("Please, enter Patient ID")
        } else if(inputFirstName.value == null){
            statusMessage.value = Event("Please, enter First Name")
        } else if(inputLastName.value == null){
            statusMessage.value = Event("Please, enter Last Name")
        } else if(inputPhoneNumber.value == null){
            statusMessage.value = Event("Please, enter Mobile Number")
        } else if(inputAddressLineOne.value == null){
            statusMessage.value = Event("Please, enter Address")
        } else if(inputCountry.value == null){
            statusMessage.value = Event("Please, enter Country")
        } else if(inputPinCode.value == null){
            statusMessage.value = Event("Please, enter PinCode")
        }
        else {
            if (isUpdateOrDelete) {
                userToUpdateOrDelete.userFirstName = inputFirstName.value!!
                userToUpdateOrDelete.userMiddleName = inputMiddleName.value
                userToUpdateOrDelete.userLastName = inputLastName.value!!
                userToUpdateOrDelete.userMobileNumber = inputPhoneNumber.value!!
                userToUpdateOrDelete.userAge = inputAge.value
                userToUpdateOrDelete.userDOB = inputDOB.value
                userToUpdateOrDelete.userSex = inputSex.value!!
                userToUpdateOrDelete.userAddressLineOne = inputAddressLineOne.value!!
                userToUpdateOrDelete.userAddressLineTwo = inputAddressLineTwo.value
                userToUpdateOrDelete.userAddressLineThree = inputAddressLineThree.value
                userToUpdateOrDelete.userCountry = inputCountry.value!!
                userToUpdateOrDelete.userPinCode = inputPinCode.value!!
                update(userToUpdateOrDelete)
            } else {
                val userPatientId = inputPatientId.value!!
                val userFirstName = inputFirstName.value!!
                val userMiddleName = inputMiddleName.value
                val userLastName = inputLastName.value!!
                val userMobileNumber = inputPhoneNumber.value!!
                val userAge = inputAge.value
                val userDOB = inputDOB.value
                val userSex = inputSex.value!!
                val userAddressLineOne = inputAddressLineOne.value!!
                val userAddressLineTwo = inputAddressLineTwo.value
                val userAddressLineThree = inputAddressLineThree.value
                val userCountry = inputCountry.value!!
                val userPinCode = inputPinCode.value!!
                val screeningDate = screeningDate.value!!
                val userDiabetes = userDiabetes.value!!
                val userTobaccoUser = userTobaccoUser.value!!
                val cardiovascularEvent = cardiovascularEvent.value!!
                val userBloodPressure = userBloodPressure.value!!
                val userHeight = userHeight.value!!
                val userWeight = userWeight.value!!
                val userBMI = userBMI.value!!
                val totalCholesterol = totalCholesterol.value!!
                val cvdScore = cvdScore.value!!
                insert(User(
                    Id = 0,
                    userPatientId = userPatientId,
                    userFirstName = userFirstName,
                    userMiddleName = userMiddleName,
                    userLastName = userLastName,
                    userMobileNumber =userMobileNumber,
                    userDOB = userDOB,
                    userAge = userAge,
                    userSex =userSex,
                    userAddressLineOne = userAddressLineOne,
                    userAddressLineTwo = userAddressLineTwo,
                    userAddressLineThree =userAddressLineThree,
                    userCountry = userCountry,
                    userPinCode =userPinCode,
                    screeningDate = screeningDate,
                    userDiabetes = userDiabetes,
                    userTobaccoUser = userTobaccoUser,
                    cardiovascularEvent = cardiovascularEvent,
                    userBloodPressure = userBloodPressure,
                    userHeight = userHeight,
                    userWeight = userWeight,
                    userBMI = userBMI,
                    totalCholesterol = totalCholesterol,
                    cvdScore = cvdScore,
                ))

            }
        }

    }

    private fun insert(user: User): Job = viewModelScope.launch{
        val newRowId: Long = cvdRiskRepository.insert(user)
        if(newRowId > -1) {
            statusMessage.value = Event("User added successfully $newRowId")
        } else {
            statusMessage.value = Event("Error occurred")
        }
    }
    private fun update(user: User): Job = viewModelScope.launch {
        val noOfRows: Int = cvdRiskRepository.update(user)
        if(noOfRows > 0) {
            inputPatientId.value = null
            inputFirstName.value = null
            inputMiddleName.value = null
            inputLastName.value = null
            inputPhoneNumber.value = null
            inputAge.value = null
            inputSex.value = null
            inputAddressLineOne.value = null
            inputAddressLineTwo.value = null
            inputAddressLineThree.value = null
            inputCountry.value = null
            inputPinCode.value = null
            isUpdateOrDelete = false
            saveOrUpdateBT.value = "Save"
            clearAllOrDeleteBT.value = "Clear All"
            statusMessage.value = Event("$noOfRows updated successfully")
        }
        else {
            statusMessage.value = Event("Error occurred")
        }
    }

    fun delete(user: User): Job = viewModelScope.launch {

        val noOfRowsDeleted: Int = cvdRiskRepository.delete(user)
        if(noOfRowsDeleted > 0) {
            inputPatientId.value = null
            inputFirstName.value = null
            inputMiddleName.value = null
            inputLastName.value = null
            inputPhoneNumber.value = null
            inputAge.value = null
            inputSex.value = null
            inputAddressLineOne.value = null
            inputAddressLineTwo.value = null
            inputAddressLineThree.value = null
            inputCountry.value = null
            inputPinCode.value = null
            isUpdateOrDelete = false
            saveOrUpdateBT.value = "Save"
            clearAllOrDeleteBT.value = "Clear all"
            statusMessage.value = Event("User deleted successfully")
            Log.d("DELETED", noOfRowsDeleted.toString())
        } else {
            statusMessage.value = Event("Error occurred")
        }
    }

    fun initUpdateAndDelete(user: User){

        inputPatientId.value = user.userPatientId
        inputFirstName.value = user.userFirstName
        inputMiddleName.value = user.userMiddleName
        inputLastName.value = user.userLastName
        inputPhoneNumber.value = user.userMobileNumber
        inputAge.value = user.userAge
        inputSex.value = user.userSex
        inputAddressLineOne.value = user.userAddressLineOne
        inputAddressLineTwo.value = user.userAddressLineTwo
        inputAddressLineThree.value = user.userAddressLineThree
        inputCountry.value = user.userCountry
        inputPinCode.value = user.userPinCode
        isUpdateOrDelete = true
        userToUpdateOrDelete = user
        saveOrUpdateBT.value = "Update"
        clearAllOrDeleteBT.value = "Delete"

    }

    fun clearAll(): Job = viewModelScope.launch {
        val rowsDeleted: Int = cvdRiskRepository.deleteAll()
        if(rowsDeleted > 0) {
            statusMessage.value = Event("All User deleted successfully")
        } else {
            statusMessage.value = Event("Error occurred")
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}