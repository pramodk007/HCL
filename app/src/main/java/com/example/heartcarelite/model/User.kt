package com.example.heartcarelite.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@kotlinx.parcelize.Parcelize
@Entity
data class User (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int,

    @ColumnInfo(name = "user_patient_id")
    var userPatientId:String,

    @ColumnInfo(name = "user_first_name")
    var userFirstName:String,

    @ColumnInfo(name = "user_middle_name")
    var userMiddleName:String?,

    @ColumnInfo(name = "user_last_name")
    var userLastName:String,

    @ColumnInfo(name = "user_DOB")
    var userDOB:String?,

    @ColumnInfo(name = "user_age")
    var userAge:String?,

    @ColumnInfo(name = "user_sex")
    var userSex:String,

    @ColumnInfo(name = "user_mobile_number")
    var userMobileNumber:String,

    @ColumnInfo(name = "user_addressLineOne")
    var userAddressLineOne:String,

    @ColumnInfo(name = "user_addressLineTwo")
    var userAddressLineTwo:String?,

    @ColumnInfo(name = "user_addressLineThree")
    var userAddressLineThree:String?,

    @ColumnInfo(name = "user_country")
    var userCountry:String,

    @ColumnInfo(name = "user_pin_code")
    var userPinCode:String,

    @ColumnInfo(name = "user_screening_date")
    var screeningDate:String,

    @ColumnInfo(name = "user_diabetes")
    var userDiabetes:String,

    @ColumnInfo(name = "user_tobacco_user")
    var userTobaccoUser:String,

    @ColumnInfo(name = "user_cardiovascular_event")
    var cardiovascularEvent:String,

    @ColumnInfo(name = "user_blood_pressure")
    var userBloodPressure:String,

    @ColumnInfo(name = "user_height")
    var userHeight:String,

    @ColumnInfo(name = "user_weight")
    var userWeight:String,

    @ColumnInfo(name = "user_bmi")
    var userBMI:String,

    @ColumnInfo(name = "user_cholesterol")
    var totalCholesterol:String,

    @ColumnInfo(name = "user_cvd_score")
    var cvdScore:String

    ) : Parcelable