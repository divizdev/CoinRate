package ru.divizdev.coinrate.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoinRate {

    //    id: "viberate",
//    name: "Viberate",
//    symbol: "VIB",
//    rank: "201",
//    price_usd: "0.522795",
//    price_btc: "0.00003514",
//    24h_volume_usd: "18734600.0",
//    market_cap_usd: "84849525.0",
//    available_supply: "162299801.0",
//    total_supply: "200000000.0",
//    max_supply: null,
//    percent_change_1h: "-1.06",
//    percent_change_24h: "15.7",
//    percent_change_7d: "23.5",
//    last_updated: "1515007154"

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
    private double _price;

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
    private double _availableSupply;

    @SerializedName("total_supply")
    @Expose
    private double _totalSupply;
    @SerializedName("max_supply")
    @Expose
    private double _maxSupply;


    @SerializedName("last_updated")
    @Expose
    private int _lastUpdated;

    public CoinRate(String id, String name, String symbol, int rank, double price, double percentChange1h, double percentChange24h, double percentChange7d, double availableSupply, double totalSupply, double maxSupply, int lastUpdated) {
        _id = id;
        _name = name;
        _symbol = symbol;
        _rank = rank;
        _price = price;
        _percentChange1h = percentChange1h;
        _percentChange24h = percentChange24h;
        _percentChange7d = percentChange7d;
        _availableSupply = availableSupply;
        _totalSupply = totalSupply;
        _maxSupply = maxSupply;
        _lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "CoinRate{" +
                "_id='" + _id + '\'' +
                ", _name='" + _name + '\'' +
                ", _symbol='" + _symbol + '\'' +
                ", _rank=" + _rank +
                ", _price=" + _price +
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

        CoinRate coinRate = (CoinRate) o;

        if (getRank() != coinRate.getRank()) return false;
        if (Double.compare(coinRate.getPrice(), getPrice()) != 0) return false;
        if (Double.compare(coinRate.getPercentChange1h(), getPercentChange1h()) != 0) return false;
        if (Double.compare(coinRate.getPercentChange24h(), getPercentChange24h()) != 0)
            return false;
        if (Double.compare(coinRate.getPercentChange7d(), getPercentChange7d()) != 0) return false;
        if (Double.compare(coinRate.getAvailableSupply(), getAvailableSupply()) != 0) return false;
        if (Double.compare(coinRate.getTotalSupply(), getTotalSupply()) != 0) return false;
        if (Double.compare(coinRate.getMaxSupply(), getMaxSupply()) != 0) return false;
        if (getLastUpdated() != coinRate.getLastUpdated()) return false;
        if (getId() != null ? !getId().equals(coinRate.getId()) : coinRate.getId() != null)
            return false;
        if (getName() != null ? !getName().equals(coinRate.getName()) : coinRate.getName() != null)
            return false;
        return getSymbol() != null ? getSymbol().equals(coinRate.getSymbol()) : coinRate.getSymbol() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getSymbol() != null ? getSymbol().hashCode() : 0);
        result = 31 * result + getRank();
        temp = Double.doubleToLongBits(getPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getPercentChange1h());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getPercentChange24h());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getPercentChange7d());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getAvailableSupply());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getTotalSupply());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getMaxSupply());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getLastUpdated();
        return result;
    }

    public double getAvailableSupply() {
        return _availableSupply;
    }

    public void setAvailableSupply(double availableSupply) {
        _availableSupply = availableSupply;
    }

    public double getTotalSupply() {
        return _totalSupply;
    }

    public void setTotalSupply(double totalSupply) {
        _totalSupply = totalSupply;
    }

    public double getMaxSupply() {
        return _maxSupply;
    }

    public void setMaxSupply(double maxSupply) {
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

    public double getPrice() {
        return _price;
    }

    public void setPrice(double price) {
        _price = price;
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
}
