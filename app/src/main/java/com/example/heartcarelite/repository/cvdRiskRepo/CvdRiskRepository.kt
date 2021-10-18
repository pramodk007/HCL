package com.example.heartcarelite.repository.cvdRiskRepo

import androidx.lifecycle.LiveData
import com.example.heartcarelite.model.CVDRiskChart
import com.example.heartcarelite.model.User
import com.example.heartcarelite.model.UserInfo

interface CvdRiskRepository {

    suspend fun insert(user: User): Long

    suspend fun update(user: User): Int

    suspend fun delete(user: User): Int

    suspend fun deleteAll(): Int

    fun getUserDetails(username: String?) : LiveData<User>

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
    ) : List<CVDRiskChart>


}