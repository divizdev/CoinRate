package ru.divizdev.coinrate.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiData(
    @SerializedName("data")
    @Expose
    var data: List<Data>? = null,

    @SerializedName("status")
    @Expose
    var status: Status? = null,
)