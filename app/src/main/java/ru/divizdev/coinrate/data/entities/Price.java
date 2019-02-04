
package ru.divizdev.coinrate.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Price {


    @SerializedName("price")
    @Expose
    private BigDecimal price;
    @SerializedName("volume_24h")
    @Expose
    private BigDecimal volume24h;
    @SerializedName("percent_change_1h")
    @Expose
    private BigDecimal percentChange1h;
    @SerializedName("percent_change_24h")
    @Expose
    private BigDecimal percentChange24h;
    @SerializedName("percent_change_7d")
    @Expose
    private BigDecimal percentChange7d;
    @SerializedName("market_cap")
    @Expose
    private BigDecimal marketCap;
    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getVolume24h() {
        return volume24h;
    }

    public void setVolume24h(BigDecimal volume24h) {
        this.volume24h = volume24h;
    }

    public BigDecimal getPercentChange1h() {
        return percentChange1h;
    }

    public void setPercentChange1h(BigDecimal percentChange1h) {
        this.percentChange1h = percentChange1h;
    }

    public BigDecimal getPercentChange24h() {
        return percentChange24h;
    }

    public void setPercentChange24h(BigDecimal percentChange24h) {
        this.percentChange24h = percentChange24h;
    }

    public BigDecimal getPercentChange7d() {
        return percentChange7d;
    }

    public void setPercentChange7d(BigDecimal percentChange7d) {
        this.percentChange7d = percentChange7d;
    }

    public BigDecimal getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(BigDecimal marketCap) {
        this.marketCap = marketCap;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Price(BigDecimal price, BigDecimal volume24h, BigDecimal percentChange1h, BigDecimal percentChange24h, BigDecimal percentChange7d, BigDecimal marketCap, String lastUpdated) {
        this.price = price;
        this.volume24h = volume24h;
        this.percentChange1h = percentChange1h;
        this.percentChange24h = percentChange24h;
        this.percentChange7d = percentChange7d;
        this.marketCap = marketCap;
        this.lastUpdated = lastUpdated;
    }

    public Price() {
    }

    public static Price getEmpety(){
        return new Price(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO, "" );
    }

}
