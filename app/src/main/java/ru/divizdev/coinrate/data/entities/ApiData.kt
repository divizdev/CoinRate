package ru.divizdev.coinrate.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiData {
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null

    @SerializedName("status")
    @Expose
    var status: Status? = null
}