package ru.divizdev.coinrate.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

class Price {
    @SerializedName("price")
    @Expose
    var price: BigDecimal? = null

    @SerializedName("volume_24h")
    @Expose
    var volume24h: BigDecimal? = null

    @SerializedName("percent_change_1h")
    @Expose
    var percentChange1h: BigDecimal? = null

    @SerializedName("percent_change_24h")
    @Expose
    var percentChange24h: BigDecimal? = null

    @SerializedName("percent_change_7d")
    @Expose
    var percentChange7d: BigDecimal? = null

    @SerializedName("market_cap")
    @Expose
    var marketCap: BigDecimal? = null

    @SerializedName("last_updated")
    @Expose
    var lastUpdated: String? = null

    constructor(
        price: BigDecimal?,
        volume24h: BigDecimal?,
        percentChange1h: BigDecimal?,
        percentChange24h: BigDecimal?,
        percentChange7d: BigDecimal?,
        marketCap: BigDecimal?,
        lastUpdated: String?
    ) {
        this.price = price
        this.volume24h = volume24h
        this.percentChange1h = percentChange1h
        this.percentChange24h = percentChange24h
        this.percentChange7d = percentChange7d
        this.marketCap = marketCap
        this.lastUpdated = lastUpdated
    }

    constructor() {}


    companion object {
        val empty: Price
            get() = Price(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, "")
    }
}