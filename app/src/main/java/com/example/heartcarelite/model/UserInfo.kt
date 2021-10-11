package com.example.heartcarelite.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import java.util.*

@kotlinx.parcelize.Parcelize
data class UserInfo(
    var screeningDate:String,
    var userDOB:String?,
    var userAge:String?,
    var userSex:String,
    var userDiabetes:String,
    var userTobaccoUser:String,
    var cardiovascularEvent:String,
    var userBloodPressure:String,
    var userHeight:String,
    var userWeight:String,
    var userBMI:String,
    var totalCholesterol:String,
    var cvdScore:String

) : Parcelable