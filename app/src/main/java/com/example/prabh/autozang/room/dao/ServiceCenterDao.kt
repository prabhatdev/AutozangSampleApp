package com.example.prabh.autozang.room.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.prabh.autozang.room.table.ServiceCenters

@Dao
interface ServiceCenterDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(serviceCentres:ArrayList<ServiceCenters>)

    @Query("SELECT * FROM ServiceCenters ORDER BY name")
    fun getData():LiveData<List<ServiceCenters>>

    @Query("SELECT * FROM ServiceCenters ORDER BY distance")
    fun getSortedDataDistance():List<ServiceCenters>

    @Query("SELECT * FROM ServiceCenters ORDER BY reviews")
    fun getSortedDataReviews():List<ServiceCenters>

    @Query("SELECT * FROM ServiceCenters ORDER BY name")
    fun getSortedDataName():List<ServiceCenters>

}