package ru.divizdev.coinrate.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class CoinRateApi implements Parcelable {

    //    id: "viberate",
//    name: "Viberate",
//    symbol: "VIB",
//    rank: "201",
//    price_usd: "0.522795",
//    price_btc: "0.00003514",
//    24h_volume_usd: "18734600.0",
//    _marketCapUsd: "84849525.0",
//    available_supply: "162299801.0",
//    total_supply: "200000000.0",
//    max_supply: null,
//    percent_change_1h: "-1.06",
//    percent_change_24h: "15.7",
//    percent_change_7d: "23.5",
//    last_updated: "1515007154"

    //region Fields
    @SerializedName("id")
    @Expose
    private String _id;

    @SerializedName("name")
    @Expose
    private String _name;

    @SerializedName("symbol")
    @Expose
    private String _symbol;

    @SerializedName("rank")
    @Expose
    private int _rank;

    @SerializedName("price_usd")
    @Expose
    private BigDecimal _priceUsd;

    @SerializedName("market_cap_usd")
    @Expose
    private BigDecimal _marketCapUsd;

    @SerializedName(value = "market_cap_rub", alternate = {"market_cap_eur"})
    @Expose
    private BigDecimal _marketCapAlternate;


    @SerializedName("percent_change_1h")
    @Expose
    private double _percentChange1h;

    @SerializedName("percent_change_24h")
    @Expose
    private double _percentChange24h;

    @SerializedName("percent_change_7d")
    @Expose
    private double _percentChange7d;

    @SerializedName("available_supply")
    @Expose
    private BigDecimal _availableSupply;

    @SerializedName("total_supply")
    @Expose
    private BigDecimal _totalSupply;
    @SerializedName("max_supply")
    @Expose
    private BigDecimal _maxSupply;


    @SerializedName("last_updated")
    @Expose
    private int _lastUpdated;

    @SerializedName(value = "price_rub", alternate = {"price_eur"})
    @Expose
    private BigDecimal _priceAlternate;



    @SerializedName("24h_volume_usd")
    @Expose
    private BigDecimal _volume24hUsd;

    @SerializedName(value = "24h_volume_rub", alternate = {"24h_volume_eur"})
    @Expose
    private BigDecimal _volume24hAlternate;
    //endregion




    //region MethodClass
    @Override
    public String toString() {
        return "ICoinRateApi{" +
                "_id='" + _id + '\'' +
                ", _name='" + _name + '\'' +
                ", _symbol='" + _symbol + '\'' +
                ", _rank=" + _rank +
                ", _priceUsd=" + _priceUsd +
                ", _percentChange1h=" + _percentChange1h +
                ", _percentChange24h=" + _percentChange24h +
                ", _percentChange7d=" + _percentChange7d +
                ", _availableSupply=" + _availableSupply +
                ", _totalSupply=" + _totalSupply +
                ", _maxSupply=" + _maxSupply +
                ", _lastUpdated=" + _lastUpdated +
                '}';
    }



    //endregion


    //region GetSetMethod
    public BigDecimal getAvailableSupply() {
        return _availableSupply;
    }

    public void setAvailableSupply(BigDecimal availableSupply) {
        _availableSupply = availableSupply;
    }

    public BigDecimal getTotalSupply() {
        return _totalSupply;
    }

    public void setTotalSupply(BigDecimal totalSupply) {
        _totalSupply = totalSupply;
    }

    public BigDecimal getMaxSupply() {
        return _maxSupply;
    }

    public void setMaxSupply(BigDecimal maxSupply) {
        _maxSupply = maxSupply;
    }

    public int getLastUpdated() {
        return _lastUpdated;
    }

    public void setLastUpdated(int lastUpdated) {
        _lastUpdated = lastUpdated;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getSymbol() {
        return _symbol;
    }

    public void setSymbol(String symbol) {
        _symbol = symbol;
    }

    public int getRank() {
        return _rank;
    }

    public void setRank(int rank) {
        _rank = rank;
    }


    public BigDecimal getPriceUsd() {
        return _priceUsd;
    }

    public void setPriceUsd(BigDecimal priceUsd) {
        _priceUsd = priceUsd;
    }

    public double getPercentChange1h() {
        return _percentChange1h;
    }

    public void setPercentChange1h(double percentChange1h) {
        _percentChange1h = percentChange1h;
    }

    public double getPercentChange24h() {
        return _percentChange24h;
    }

    public void setPercentChange24h(double percentChange24h) {
        _percentChange24h = percentChange24h;
    }

    public double getPercentChange7d() {
        return _percentChange7d;
    }

    public void setPercentChange7d(double percentChange7d) {
        _percentChange7d = percentChange7d;
    }

    public BigDecimal getMarketCapUsd() {
        return _marketCapUsd;
    }

    public void setMarketCapUsd(BigDecimal marketCapUsd) {
        _marketCapUsd = marketCapUsd;
    }

    public BigDecimal getMarketCapAlternate() {
        return _marketCapAlternate;
    }

    public void setMarketCapAlternate(BigDecimal marketCapAlternate) {
        _marketCapAlternate = marketCapAlternate;
    }



    public BigDecimal getVolume24hUsd() {
        return _volume24hUsd;
    }

    public void setVolume24hUsd(BigDecimal volume24hUsd) {
        _volume24hUsd = volume24hUsd;
    }

    public BigDecimal getVolume24hAlternate() {
        return _volume24hAlternate;
    }

    public void setVolume24hAlternate(BigDecimal volume24hAlternate) {
        _volume24hAlternate = volume24hAlternate;
    }

    //endregion


    //region Parcel
    private CoinRateApi(Parcel parcel) {
        _id = parcel.readString();
        _name = parcel.readString();
        _symbol = parcel.readString();
        _rank = parcel.readInt();
        _priceUsd = BigDecimal.valueOf(parcel.readDouble());
        _percentChange1h = parcel.readDouble();
        _percentChange24h = parcel.readDouble();
        _percentChange7d = parcel.readDouble();
        _availableSupply = BigDecimal.valueOf(parcel.readDouble());
        _totalSupply = BigDecimal.valueOf(parcel.readDouble());
        _maxSupply = BigDecimal.valueOf(parcel.readDouble());
        _lastUpdated = parcel.readInt();
        _priceAlternate = BigDecimal.valueOf(parcel.readDouble());

        _marketCapUsd = BigDecimal.valueOf(parcel.readDouble());
        _marketCapAlternate = BigDecimal.valueOf(parcel.readDouble());

        _volume24hUsd = BigDecimal.valueOf(parcel.readDouble());
        _volume24hAlternate = BigDecimal.valueOf(parcel.readDouble());


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(_id);
        parcel.writeString(_name);
        parcel.writeString(_symbol);
        parcel.writeInt(_rank);
        parcel.writeDouble(_priceUsd.doubleValue());
        parcel.writeDouble(_percentChange1h);
        parcel.writeDouble(_percentChange24h);
        parcel.writeDouble(_percentChange7d);
        parcel.writeDouble(_availableSupply.doubleValue());
        parcel.writeDouble(_totalSupply.doubleValue());
        parcel.writeDouble(_maxSupply.doubleValue());
        parcel.writeInt(_lastUpdated);
        parcel.writeDouble(_priceAlternate.doubleValue());

        parcel.writeDouble(_marketCapUsd.doubleValue());
        parcel.writeDouble(_marketCapAlternate.doubleValue());

        parcel.writeDouble(_volume24hUsd.doubleValue());
        parcel.writeDouble(_volume24hAlternate.doubleValue());

    }

    public static final Parcelable.Creator<CoinRateApi> CREATOR = new Creator<CoinRateApi>() {
        @Override
        public CoinRateApi createFromParcel(Parcel parcel) {
            return new CoinRateApi(parcel);
        }

        @Override
        public CoinRateApi[] newArray(int size) {
            return new CoinRateApi[size];
        }
    };

    public BigDecimal getPriceAlternate() {
        return _priceAlternate;
    }

    public void setPriceAlternate(BigDecimal priceAlternate) {
        _priceAlternate = priceAlternate;
    }

    public BigDecimal getPriceEur() {
        return BigDecimal.ZERO;
}

    public void setPriceEur(BigDecimal priceEur) {

    }
    //endregion
}
