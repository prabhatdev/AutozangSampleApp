package com.example.prabh.autozang.room.table

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.reactivex.annotations.NonNull

@Entity
class ServiceCenters
{

    @SerializedName("distance")
    @Expose
    var distance:Float?=null

    @SerializedName("location")
    @Expose
    var location:String?=null

    @PrimaryKey
    @NonNull
    @SerializedName("name")
    @Expose
    lateinit var name:String

    @SerializedName("price")
    @Expose
    var price:Float?=null

    @SerializedName("reviews")
    @Expose
    var reviews:Int?=null

    @SerializedName("timings")
    @Expose
    var timings:String?=null

}