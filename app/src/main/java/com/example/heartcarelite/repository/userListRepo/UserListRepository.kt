package com.example.heartcarelite.repository.userListRepo

import androidx.lifecycle.LiveData
import com.example.heartcarelite.model.User
import com.example.heartcarelite.model.UserInfo

interface UserListRepository {

    var userInfoListModel: LiveData<List<User>>

}