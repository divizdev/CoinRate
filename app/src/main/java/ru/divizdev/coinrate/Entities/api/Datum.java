
package ru.divizdev.coinrate.Entities.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("cmc_rank")
    @Expose
    private Integer cmcRank;
    @SerializedName("num_market_pairs")
    @Expose
    private BigDecimal numMarketPairs;
    @SerializedName("circulating_supply")
    @Expose
    private BigDecimal circulatingSupply;
    @SerializedName("total_supply")
    @Expose
    private BigDecimal totalSupply;
    @SerializedName("max_supply")
    @Expose
    private BigDecimal maxSupply;
    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;
    @SerializedName("quote")
    @Expose
    private Map<String,Price> quote;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getCmcRank() {
        return cmcRank;
    }

    public void setCmcRank(Integer cmcRank) {
        this.cmcRank = cmcRank;
    }

    public BigDecimal getNumMarketPairs() {
        return numMarketPairs;
    }

    public void setNumMarketPairs(BigDecimal numMarketPairs) {
        this.numMarketPairs = numMarketPairs;
    }

    public BigDecimal getCirculatingSupply() {
        return circulatingSupply;
    }

    public void setCirculatingSupply(BigDecimal circulatingSupply) {
        this.circulatingSupply = circulatingSupply;
    }

    public BigDecimal getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(BigDecimal totalSupply) {
        this.totalSupply = totalSupply;
    }

    public BigDecimal getMaxSupply() {
        return maxSupply;
    }

    public void setMaxSupply(BigDecimal maxSupply) {
        this.maxSupply = maxSupply;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Map<String,Price> getQuote() {
        return quote;
    }

    public void setQuote(Map<String,Price> quote) {
        this.quote = quote;
    }

}
