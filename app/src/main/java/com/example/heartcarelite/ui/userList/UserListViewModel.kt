package com.example.heartcarelite.ui.userList

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import com.example.heartcarelite.repository.cvdRiskRepo.CvdRiskRepository
import com.example.heartcarelite.repository.userListRepo.UserListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val userListRepository: UserListRepository) :ViewModel(), Observable {

    val userList = userListRepository.userInfoListModel

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }
}