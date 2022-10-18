package ru.divizdev.coinrate.data.entities

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class Status {
    @SerializedName("timestamp")
    @Expose
    var timestamp: String? = null

    @SerializedName("error_code")
    @Expose
    var errorCode: Int? = null

    @SerializedName("error_message")
    @Expose
    var errorMessage: String? = null

    @SerializedName("elapsed")
    @Expose
    var elapsed: Int? = null

    @SerializedName("credit_count")
    @Expose
    var creditCount: Int? = null
}