package com.example.heartcarelite.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.heartcarelite.model.User
import com.example.heartcarelite.model.UserInfo

@Database(entities = arrayOf(User::class), version = 4, exportSchema = false)
abstract class AppDataBase:RoomDatabase() {

    abstract fun provideUserDao() : UserDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabaseClient(context: Context) : AppDataBase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, AppDataBase::class.java, "USER_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }

    }
}