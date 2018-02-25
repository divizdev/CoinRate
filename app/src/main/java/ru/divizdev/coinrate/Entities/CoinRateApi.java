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


    public CoinRateApi(String id, String name, String symbol, int rank, BigDecimal priceUsd, BigDecimal marketCapUsd, BigDecimal marketCapAlternate, double percentChange1h, double percentChange24h, double percentChange7d, BigDecimal availableSupply, BigDecimal totalSupply, BigDecimal maxSupply, int lastUpdated, BigDecimal priceAlternate, BigDecimal volume24hUsd, BigDecimal volume24hAlternate) {
        _id = id;
        _name = name;
        _symbol = symbol;
        _rank = rank;
        _priceUsd = priceUsd;
        _marketCapUsd = marketCapUsd;
        _marketCapAlternate = marketCapAlternate;
        _percentChange1h = percentChange1h;
        _percentChange24h = percentChange24h;
        _percentChange7d = percentChange7d;
        _availableSupply = availableSupply;
        _totalSupply = totalSupply;
        _maxSupply = maxSupply;
        _lastUpdated = lastUpdated;
        _priceAlternate = priceAlternate;
        _volume24hUsd = volume24hUsd;
        _volume24hAlternate = volume24hAlternate;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoinRateApi that = (CoinRateApi) o;

        if (getRank() != that.getRank()) return false;
        if (Double.compare(that.getPercentChange1h(), getPercentChange1h()) != 0) return false;
        if (Double.compare(that.getPercentChange24h(), getPercentChange24h()) != 0) return false;
        if (Double.compare(that.getPercentChange7d(), getPercentChange7d()) != 0) return false;
        if (getLastUpdated() != that.getLastUpdated()) return false;
        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
            return false;
        if (getSymbol() != null ? !getSymbol().equals(that.getSymbol()) : that.getSymbol() != null)
            return false;
        if (getPriceUsd() != null ? !getPriceUsd().equals(that.getPriceUsd()) : that.getPriceUsd() != null)
            return false;
        if (getMarketCapUsd() != null ? !getMarketCapUsd().equals(that.getMarketCapUsd()) : that.getMarketCapUsd() != null)
            return false;
        if (getMarketCapAlternate() != null ? !getMarketCapAlternate().equals(that.getMarketCapAlternate()) : that.getMarketCapAlternate() != null)
            return false;
        if (getAvailableSupply() != null ? !getAvailableSupply().equals(that.getAvailableSupply()) : that.getAvailableSupply() != null)
            return false;
        if (getTotalSupply() != null ? !getTotalSupply().equals(that.getTotalSupply()) : that.getTotalSupply() != null)
            return false;
        if (getMaxSupply() != null ? !getMaxSupply().equals(that.getMaxSupply()) : that.getMaxSupply() != null)
            return false;
        if (getPriceAlternate() != null ? !getPriceAlternate().equals(that.getPriceAlternate()) : that.getPriceAlternate() != null)
            return false;
        if (getVolume24hUsd() != null ? !getVolume24hUsd().equals(that.getVolume24hUsd()) : that.getVolume24hUsd() != null)
            return false;
        return getVolume24hAlternate() != null ? getVolume24hAlternate().equals(that.getVolume24hAlternate()) : that.getVolume24hAlternate() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getSymbol() != null ? getSymbol().hashCode() : 0);
        result = 31 * result + getRank();
        result = 31 * result + (getPriceUsd() != null ? getPriceUsd().hashCode() : 0);
        result = 31 * result + (getMarketCapUsd() != null ? getMarketCapUsd().hashCode() : 0);
        result = 31 * result + (getMarketCapAlternate() != null ? getMarketCapAlternate().hashCode() : 0);
        temp = Double.doubleToLongBits(getPercentChange1h());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getPercentChange24h());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getPercentChange7d());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getAvailableSupply() != null ? getAvailableSupply().hashCode() : 0);
        result = 31 * result + (getTotalSupply() != null ? getTotalSupply().hashCode() : 0);
        result = 31 * result + (getMaxSupply() != null ? getMaxSupply().hashCode() : 0);
        result = 31 * result + getLastUpdated();
        result = 31 * result + (getPriceAlternate() != null ? getPriceAlternate().hashCode() : 0);
        result = 31 * result + (getVolume24hUsd() != null ? getVolume24hUsd().hashCode() : 0);
        result = 31 * result + (getVolume24hAlternate() != null ? getVolume24hAlternate().hashCode() : 0);
        return result;
    }

    //endregion


    //region GetSetMethod
    public BigDecimal getAvailableSupply() {
        return checkAndReturnValue(_availableSupply);
    }

    public void setAvailableSupply(BigDecimal availableSupply) {
        _availableSupply = availableSupply;
    }

    public BigDecimal getTotalSupply() {
        return checkAndReturnValue(_totalSupply);
    }

    public void setTotalSupply(BigDecimal totalSupply) {
        _totalSupply = totalSupply;
    }

    public BigDecimal getMaxSupply() {
        return checkAndReturnValue(_maxSupply);
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
        return checkAndReturnValue(_priceUsd);
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
        return checkAndReturnValue(_marketCapUsd);
    }

    public void setMarketCapUsd(BigDecimal marketCapUsd) {
        _marketCapUsd = marketCapUsd;
    }

    public BigDecimal getMarketCapAlternate() {

        return checkAndReturnValue(_marketCapAlternate);
    }

    public void setMarketCapAlternate(BigDecimal marketCapAlternate) {
        _marketCapAlternate = marketCapAlternate;
    }



    public BigDecimal getVolume24hUsd() {

        return checkAndReturnValue(_volume24hUsd);
    }

    public void setVolume24hUsd(BigDecimal volume24hUsd) {
        _volume24hUsd = volume24hUsd;
    }

    public BigDecimal getVolume24hAlternate() {

        return checkAndReturnValue(_volume24hAlternate);
    }

    public void setVolume24hAlternate(BigDecimal volume24hAlternate) {
        _volume24hAlternate = volume24hAlternate;
    }

    public BigDecimal getPriceAlternate() {

        return checkAndReturnValue(_priceAlternate);
    }

    public void setPriceAlternate(BigDecimal priceAlternate) {
        _priceAlternate = priceAlternate;
    }

    //endregion

    private BigDecimal checkAndReturnValue(BigDecimal value){
        if(value == null){
            return BigDecimal.ZERO;
        }
        return value;
    }

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
        parcel.writeString(getId());
        parcel.writeString(getName());
        parcel.writeString(getSymbol());
        parcel.writeInt(getRank());
        parcel.writeDouble(getPriceUsd().doubleValue());
        parcel.writeDouble(getPercentChange1h());
        parcel.writeDouble(getPercentChange24h());
        parcel.writeDouble(getPercentChange7d());
        parcel.writeDouble(getAvailableSupply().doubleValue());
        parcel.writeDouble(getTotalSupply().doubleValue());
        parcel.writeDouble(getMaxSupply().doubleValue());
        parcel.writeInt(getLastUpdated());
        parcel.writeDouble(getPriceAlternate().doubleValue());

        parcel.writeDouble(getMarketCapUsd().doubleValue());
        parcel.writeDouble(getMarketCapAlternate().doubleValue());

        parcel.writeDouble(getVolume24hUsd().doubleValue());
        parcel.writeDouble(getVolume24hAlternate().doubleValue());

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




    //endregion
}
