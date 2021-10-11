package com.example.heartcarelite.repository.userListRepo

import androidx.lifecycle.LiveData
import com.example.heartcarelite.data.local.UserDAO
import com.example.heartcarelite.model.UserInfo
import javax.inject.Inject

class UserListRepositoryImp @Inject constructor(private val userDao: UserDAO) :UserListRepository{

    override var userInfoListModel = userDao.getAllUser()


}