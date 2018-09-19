package com.example.prabh.autozang.room.handler

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.prabh.autozang.room.dao.ServiceCenterDao
import com.example.prabh.autozang.room.table.ServiceCenters

@Database(entities = [ServiceCenters::class], version = 1)
abstract class AutozangDatabase : RoomDatabase() {
    abstract fun serviceCenterDao():ServiceCenterDao
    
    companion object {
        private const val DB_NAME = "autozang.db"

        private var INSTANCE: AutozangDatabase? = null

        fun getInstance(context: Context): AutozangDatabase? {
            if (INSTANCE == null) {
                synchronized(AutozangDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AutozangDatabase::class.java, DB_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE
        }

        fun deleteAllTables() {
            INSTANCE?.clearAllTables()
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}