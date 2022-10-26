package ru.divizdev.coinrate.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class Price(
    @SerializedName("price")
    @Expose
    var price: BigDecimal? = null,

    @SerializedName("volume_24h")
    @Expose
    var volume24h: BigDecimal? = null,

    @SerializedName("percent_change_1h")
    @Expose
    var percentChange1h: BigDecimal? = null,

    @SerializedName("percent_change_24h")
    @Expose
    var percentChange24h: BigDecimal? = null,

    @SerializedName("percent_change_7d")
    @Expose
    var percentChange7d: BigDecimal? = null,

    @SerializedName("market_cap")
    @Expose
    var marketCap: BigDecimal? = null,

    @SerializedName("last_updated")
    @Expose
    var lastUpdated: String? = null,
) {
    companion object {
        val empty: Price
            get() = Price(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, "")
    }
}