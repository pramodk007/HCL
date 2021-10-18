package com.example.heartcarelite.repository.cvdRiskRepo

import androidx.lifecycle.LiveData
import com.example.heartcarelite.data.local.CVDRiskDataDAO
import com.example.heartcarelite.data.local.UserDAO
import com.example.heartcarelite.model.CVDRiskChart
import com.example.heartcarelite.model.User
import com.example.heartcarelite.model.UserInfo
import javax.inject.Inject

class CvdRiskRepositoryImp @Inject constructor(private val userDao: UserDAO,private val cvdRiskDataDAO: CVDRiskDataDAO):CvdRiskRepository {

    override fun getUserDetails(username: String?): LiveData<User> {
        val userInfoModel = userDao.getUserDetails(username)
        return userInfoModel
    }

    override fun getCvdDataDetails(
        regionCode:String,
        isDiabetes:String,
        gender:String,
        isSmoker:String,
        age:String,
        systolic:String,
        cholesterol:String,
        cholesterolUnit:String,
        BMI:String,
        BMIUnit:String,): List<CVDRiskChart> {
        return cvdRiskDataDAO.getCvdDataDetails(
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

    override suspend fun insert(user: User): Long{
        return userDao.insertUser(user)
    }
    override suspend fun update(user: User): Int{
        return userDao.updateUser(user)
    }
    override suspend fun delete(user: User): Int{
        return userDao.deleteUser(user)
    }
    override suspend fun deleteAll(): Int{
        return userDao.deleteAll()
    }

}