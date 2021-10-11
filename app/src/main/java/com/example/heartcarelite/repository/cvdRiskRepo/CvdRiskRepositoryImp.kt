package com.example.heartcarelite.repository.cvdRiskRepo

import androidx.lifecycle.LiveData
import com.example.heartcarelite.data.local.UserDAO
import com.example.heartcarelite.model.User
import com.example.heartcarelite.model.UserInfo
import javax.inject.Inject

class CvdRiskRepositoryImp @Inject constructor(private val userDao: UserDAO ):CvdRiskRepository {

    override fun getUserDetails(username: String?): LiveData<User> {
        val userInfoModel = userDao.getUserDetails(username)
        return userInfoModel
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