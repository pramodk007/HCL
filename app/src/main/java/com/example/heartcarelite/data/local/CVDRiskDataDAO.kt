package com.example.heartcarelite.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.heartcarelite.model.CVDRiskChart

@Dao
interface CVDRiskDataDAO {

    @Query("SELECT * FROM  CVDRiskChart WHERE " +
            "region_code =:regionCode " +
            "AND is_diabetes =:isDiabetes " +
            "AND gender =:gender " +
            "AND is_smoker = :isSmoker " +
            "AND age =:age " +
            "AND systolic =:systolic " +
            "AND cholesterol =:cholesterol " +
            "AND cholesterol_unit =:cholesterolUnit " +
            "AND BMI =:BMI " +
            "AND BMI_unit =:BMIUnit" )
    fun getCvdDataDetails(
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
    ): List<CVDRiskChart>
}