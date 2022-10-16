package ru.divizdev.coinrate.presentation.entities

import android.os.Parcel
import android.os.Parcelable
import ru.divizdev.coinrate.BuildConfig
import ru.divizdev.coinrate.R
import ru.divizdev.coinrate.data.entities.ApiData
import ru.divizdev.coinrate.data.entities.Price
import ru.divizdev.coinrate.utils.LocaleUtils
import java.math.BigDecimal
import java.math.MathContext
import java.util.*

class CoinRateUI : Parcelable {
    val currency: String?
    val id: String?
    val name: String?
    val symbol: String?
    val rank: Int
    val price: BigDecimal?
    val marketCap: BigDecimal?
    val percentChange1h: Double
    val percentChange24h: Double
    val percentChange7d: Double
    val availableSupply: BigDecimal?
    val totalSupply: BigDecimal?
    val maxSupply: BigDecimal?
    val lastUpdated: Int
    val volume24h: BigDecimal?

    constructor(
        currency: String?,
        id: String?,
        name: String?,
        symbol: String?,
        rank: Int,
        price: BigDecimal?,
        marketCap: BigDecimal?,
        percentChange1h: Double,
        percentChange24h: Double,
        percentChange7d: Double,
        availableSupply: BigDecimal?,
        totalSupply: BigDecimal?,
        maxSupply: BigDecimal?,
        lastUpdated: Int,
        volume24h: BigDecimal?
    ) {
        this.currency = currency
        this.id = id
        this.name = name
        this.symbol = symbol
        this.rank = rank
        this.price = price
        this.marketCap = marketCap
        this.percentChange1h = percentChange1h
        this.percentChange24h = percentChange24h
        this.percentChange7d = percentChange7d
        this.availableSupply = availableSupply
        this.totalSupply = totalSupply
        this.maxSupply = maxSupply
        this.lastUpdated = lastUpdated
        this.volume24h = volume24h
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as CoinRateUI
        if (rank != that.rank) return false
        if (java.lang.Double.compare(that.percentChange1h, percentChange1h) != 0) return false
        if (java.lang.Double.compare(that.percentChange24h, percentChange24h) != 0) return false
        if (java.lang.Double.compare(that.percentChange7d, percentChange7d) != 0) return false
        if (lastUpdated != that.lastUpdated) return false
        if (if (currency != null) currency != that.currency else that.currency != null) return false
        if (if (id != null) id != that.id else that.id != null) return false
        if (if (name != null) name != that.name else that.name != null) return false
        if (if (symbol != null) symbol != that.symbol else that.symbol != null) return false
        if (if (price != null) price != that.price else that.price != null) return false
        if (if (marketCap != null) marketCap != that.marketCap else that.marketCap != null) return false
        if (if (availableSupply != null) availableSupply != that.availableSupply else that.availableSupply != null) return false
        if (if (totalSupply != null) totalSupply != that.totalSupply else that.totalSupply != null) return false
        if (if (maxSupply != null) maxSupply != that.maxSupply else that.maxSupply != null) return false
        return if (volume24h != null) volume24h == that.volume24h else that.volume24h == null
    }

    override fun hashCode(): Int {
        var result: Int
        var temp: Long
        result = currency?.hashCode() ?: 0
        result = 31 * result + (id?.hashCode() ?: 0)
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (symbol?.hashCode() ?: 0)
        result = 31 * result + rank
        result = 31 * result + (price?.hashCode() ?: 0)
        result = 31 * result + (marketCap?.hashCode() ?: 0)
        temp = java.lang.Double.doubleToLongBits(percentChange1h)
        result = 31 * result + (temp xor (temp ushr 32)).toInt()
        temp = java.lang.Double.doubleToLongBits(percentChange24h)
        result = 31 * result + (temp xor (temp ushr 32)).toInt()
        temp = java.lang.Double.doubleToLongBits(percentChange7d)
        result = 31 * result + (temp xor (temp ushr 32)).toInt()
        result = 31 * result + (availableSupply?.hashCode() ?: 0)
        result = 31 * result + (totalSupply?.hashCode() ?: 0)
        result = 31 * result + (maxSupply?.hashCode() ?: 0)
        result = 31 * result + lastUpdated
        result = 31 * result + (volume24h?.hashCode() ?: 0)
        return result
    }

    val uiVolume24: String
        get() = LocaleUtils.formatBigDecimal(volume24h)
    val uiMarketCap: String
        get() = LocaleUtils.formatBigDecimal(marketCap)

    private fun getColorPercent(percent: Double): Int {
        return if (percent > 0.0) {
            R.color.colorUp
        } else {
            R.color.colorDown
        }
    }

    val uiPrice: String
        get() = LocaleUtils.formatBigDecimal(price)
    val urlImage: String
        get() = BuildConfig.IMG_URL + symbol!!.lowercase(Locale.getDefault()) + "@2x.png"
    val colorPercentChange1h: Int
        get() = getColorPercent(percentChange1h)
    val colorPercentChange24h: Int
        get() = getColorPercent(percentChange24h)
    val colorPercentChange7d: Int
        get() = getColorPercent(percentChange7d)
    val uiPercentChange1h: String
        get() = percentChange1h.toString()
    val uiPercentChange24h: String
        get() = percentChange24h.toString()
    val uiPercentChange7d: String
        get() = percentChange7d.toString()
    val uiAvailableSupply: String
        get() = LocaleUtils.formatBigDecimal(availableSupply)
    val uiTotalSupply: String
        get() = LocaleUtils.formatBigDecimal(totalSupply)
    val uiMaxSupply: String
        get() = LocaleUtils.formatBigDecimal(maxSupply)
    val uiLastUpdated: String
        get() = lastUpdated.toString()//"\uf155";//"\uf155";

    //"\uf155";
    val uiCurrency: String
        get() {
            if (currency!!.compareTo("USD") == 0) {
                return "\u0024" //"\uf155";
            }
            if (currency.compareTo("RUB") == 0) {
                return "\u20BD" //"\uf155";
            }
            return if (currency.compareTo("EUR") == 0) {
                "\u20AC" //"\uf155";
            } else currency
        }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(currency)
        dest.writeString(id)
        dest.writeString(name)
        dest.writeString(symbol)
        dest.writeInt(rank)
        dest.writeSerializable(price)
        dest.writeSerializable(marketCap)
        dest.writeDouble(percentChange1h)
        dest.writeDouble(percentChange24h)
        dest.writeDouble(percentChange7d)
        dest.writeSerializable(availableSupply)
        dest.writeSerializable(totalSupply)
        dest.writeSerializable(maxSupply)
        dest.writeInt(lastUpdated)
        dest.writeSerializable(volume24h)
    }

    protected constructor(`in`: Parcel) {
        currency = `in`.readString()
        id = `in`.readString()
        name = `in`.readString()
        symbol = `in`.readString()
        rank = `in`.readInt()
        price = `in`.readSerializable() as BigDecimal?
        marketCap = `in`.readSerializable() as BigDecimal?
        percentChange1h = `in`.readDouble()
        percentChange24h = `in`.readDouble()
        percentChange7d = `in`.readDouble()
        availableSupply = `in`.readSerializable() as BigDecimal?
        totalSupply = `in`.readSerializable() as BigDecimal?
        maxSupply = `in`.readSerializable() as BigDecimal?
        lastUpdated = `in`.readInt()
        volume24h = `in`.readSerializable() as BigDecimal?
    }

    companion object CREATOR : Parcelable.Creator<CoinRateUI> {
        override fun createFromParcel(parcel: Parcel): CoinRateUI {
            return CoinRateUI(parcel)
        }

        override fun newArray(size: Int): Array<CoinRateUI?> {
            return arrayOfNulls(size)
        }
        @JvmStatic
        fun convertList(apiData: ApiData, currency: String): List<CoinRateUI> {
            val result: MutableList<CoinRateUI> = ArrayList()
            var price = Price.empty
            for (datum in (apiData.data ?: emptyList())) {

                //TODO: ALARM hardcode
                if (currency.compareTo("USD") == 0) {
                    price = datum.quote["USD"] ?: Price.empty
                } else if (currency.compareTo("EUR") == 0) {
                    price = datum.quote["EUR"] ?: Price.empty
                } else if (currency.compareTo("RUB") == 0) {
                    price = datum.quote["RUB"] ?: Price.empty
                }
                result.add(
                    CoinRateUI(
                        currency,
                        datum.id.toString(),
                        datum.name,
                        datum.symbol,
                        datum.cmcRank ?: -1,
                        price.price,
                        price.marketCap,
                        price.percentChange1h?.round(MathContext(2))?.toDouble() ?: 0.toDouble(),
                        price.percentChange24h?.round(MathContext(2))?.toDouble() ?: 0.toDouble(),
                        price.percentChange7d?.round(MathContext(2))?.toDouble() ?: 0.toDouble(),
                        datum.circulatingSupply,
                        datum.totalSupply, datum.maxSupply, 0, price.volume24h
                    )
                )
            }
            return result
        }
    }
}