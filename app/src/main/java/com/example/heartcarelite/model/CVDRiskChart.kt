package com.example.heartcarelite.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CVDRiskChart")
data class CVDRiskChart(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id:Int,

    @ColumnInfo(name = "region_code")
    val regionCode:String,

    @ColumnInfo(name = "risk_level")
    val riskLevel:String,

    @ColumnInfo(name = "is_diabetes")
    val isDiabetes:String,

    @ColumnInfo(name = "gender")
    val gender:String,

    @ColumnInfo(name = "is_smoker")
    val isSmoker:String,

    @ColumnInfo(name = "age")
    val age:String,

    @ColumnInfo(name = "systolic")
    val systolic:String,

    @ColumnInfo(name = "cholesterol")
    val cholesterol:String,

    @ColumnInfo(name = "cholesterol_unit")
    val cholesterolUnit:String,

    @ColumnInfo(name = "BMI")
    val BMI:String,

    @ColumnInfo(name = "BMI_unit")
    val BMIUnit:String,

    @ColumnInfo(name = "hs")
    val hs:String,

    @ColumnInfo(name = "hs_value")
    val hsValue:String

)