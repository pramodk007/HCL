package com.example.heartcarelite.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.heartcarelite.model.User
import com.example.heartcarelite.model.UserInfo

@Dao
interface UserDAO {

    @Query("SELECT * FROM User WHERE user_first_name =:username")
    fun getUserDetails(username: String?) : LiveData<User>

    @Insert
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(user: User): Int

    @Delete
    suspend fun deleteUser(user: User): Int

    @Query("DELETE FROM User")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM User")
    fun getAllUser(): LiveData<List<User>>


}