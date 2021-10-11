package com.example.heartcarelite.di

import com.example.heartcarelite.data.local.UserDAO
import com.example.heartcarelite.repository.cvdRiskRepo.CvdRiskRepository
import com.example.heartcarelite.repository.cvdRiskRepo.CvdRiskRepositoryImp
import com.example.heartcarelite.repository.userListRepo.UserListRepository
import com.example.heartcarelite.repository.userListRepo.UserListRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePatientRepository(userDao: UserDAO): CvdRiskRepository {
        return CvdRiskRepositoryImp(userDao)
    }

    @Provides
    @Singleton
    fun provideUserListRepository(userDao: UserDAO): UserListRepository {
        return UserListRepositoryImp(userDao)
    }


}