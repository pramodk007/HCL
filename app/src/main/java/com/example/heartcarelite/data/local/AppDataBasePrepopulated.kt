package com.example.heartcarelite.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.heartcarelite.model.CVDRiskChart
import com.example.heartcarelite.model.User
import com.example.heartcarelite.utility.AssetSQLiteOpenHelperFactory

@Database(entities = arrayOf(CVDRiskChart::class), version = 1, exportSchema = false)
abstract class AppDataBasePrePopulated:RoomDatabase() {

    abstract fun provideCvdDataDao() : CVDRiskDataDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDataBasePrePopulated? = null

        fun getDatabaseClient(context: Context) : AppDataBasePrePopulated {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, AppDataBasePrePopulated::class.java, "cvdtable.db")
                    .openHelperFactory(AssetSQLiteOpenHelperFactory())
                    .allowMainThreadQueries()
                    .build()

                return INSTANCE!!

            }
        }

    }
}