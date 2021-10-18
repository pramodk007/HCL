package com.example.heartcarelite.di

import android.content.Context
import com.example.heartcarelite.data.local.AppDataBase
import com.example.heartcarelite.data.local.AppDataBasePrePopulated
import com.example.heartcarelite.data.local.CVDRiskDataDAO
import com.example.heartcarelite.data.local.UserDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDataBase {
        return AppDataBase.getDatabaseClient(context)
    }

    @Singleton
    @Provides
    fun provideUserDao(appDatabase: AppDataBase): UserDAO {
        return appDatabase.provideUserDao()
    }

    @Singleton
    @Provides
    fun provideAppDataBasePrePopulated(@ApplicationContext context: Context): AppDataBasePrePopulated {
        return AppDataBasePrePopulated.getDatabaseClient(context)
    }

    @Singleton
    @Provides
    fun provideCVDRiskDataDAO(appDataBasePrePopulated: AppDataBasePrePopulated): CVDRiskDataDAO {
        return appDataBasePrePopulated.provideCvdDataDao()
    }


}