package ru.divizdev.coinrate.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

class Datum {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("symbol")
    @Expose
    var symbol: String? = null

    @SerializedName("slug")
    @Expose
    var slug: String? = null

    @SerializedName("cmc_rank")
    @Expose
    var cmcRank: Int? = null

    @SerializedName("num_market_pairs")
    @Expose
    var numMarketPairs: BigDecimal? = null

    @SerializedName("circulating_supply")
    @Expose
    var circulatingSupply: BigDecimal? = null

    @SerializedName("total_supply")
    @Expose
    var totalSupply: BigDecimal? = null

    @SerializedName("max_supply")
    @Expose
    var maxSupply: BigDecimal? = null

    @SerializedName("last_updated")
    @Expose
    var lastUpdated: String? = null

    @SerializedName("date_added")
    @Expose
    var dateAdded: String? = null

    @SerializedName("tags")
    @Expose
    var tags: List<String>? = null

    @SerializedName("quote")
    @Expose
    var quote: Map<String, Price> = mutableMapOf()
}