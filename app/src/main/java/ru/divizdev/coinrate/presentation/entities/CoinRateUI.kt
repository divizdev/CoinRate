package ru.divizdev.coinrate.presentation.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.divizdev.coinrate.BuildConfig
import ru.divizdev.coinrate.R
import ru.divizdev.coinrate.data.entities.ApiData
import ru.divizdev.coinrate.data.entities.Price
import ru.divizdev.coinrate.utils.LocaleUtils
import java.math.BigDecimal
import java.math.MathContext
import java.util.*

@Parcelize
data class CoinRateUI(
    val currency: String?,
    val id: String?,
    val name: String?,
    val symbol: String?,
    val rank: Int,
    val price: BigDecimal?,
    val marketCap: BigDecimal?,
    val percentChange1h: Double,
    val percentChange24h: Double,
    val percentChange7d: Double,
    val availableSupply: BigDecimal?,
    val totalSupply: BigDecimal?,
    val maxSupply: BigDecimal?,
    val lastUpdated: Int,
    val volume24h: BigDecimal?,
) : Parcelable {


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



    companion object {
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
                        datum.totalSupply,
                        datum.maxSupply,
                        0,
                        price.volume24h
                    )
                )
            }
            return result
        }
    }
}