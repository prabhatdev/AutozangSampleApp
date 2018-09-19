package com.example.prabh.autozang.retrofit.model

import com.example.prabh.autozang.room.table.ServiceCenters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ServiceCenterResult{
    @SerializedName("result")
    @Expose
    val result:ArrayList<ServiceCenters>?=null

    @SerializedName("status")
    @Expose
    val status:String?=null

    @SerializedName("error")
    @Expose
    val error:String?=null

}

