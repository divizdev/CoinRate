package ru.divizdev.coinrate.presentation.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import ru.divizdev.coinrate.BuildConfig;
import ru.divizdev.coinrate.R;
import ru.divizdev.coinrate.data.entities.ApiData;
import ru.divizdev.coinrate.data.entities.Datum;
import ru.divizdev.coinrate.data.entities.Price;
import ru.divizdev.coinrate.utils.LocaleUtils;

public class CoinRateUI implements Parcelable {


    final private String _currency;
    final private String _id;
    final private String _name;
    final private String _symbol;
    final private int _rank;
    final private BigDecimal _price;
    final private BigDecimal _marketCap;
    final private double _percentChange1h;
    final private double _percentChange24h;
    final private double _percentChange7d;
    final private BigDecimal _availableSupply;
    final private BigDecimal _totalSupply;
    final private BigDecimal _maxSupply;
    final private int _lastUpdated;
    final private BigDecimal _volume24h;

    public CoinRateUI(String currency, String id, String name, String symbol, int rank, BigDecimal price, BigDecimal marketCap, double percentChange1h, double percentChange24h, double percentChange7d, BigDecimal availableSupply, BigDecimal totalSupply, BigDecimal maxSupply, int lastUpdated, BigDecimal volume24h) {
        _currency = currency;
        _id = id;
        _name = name;
        _symbol = symbol;
        _rank = rank;
        _price = price;
        _marketCap = marketCap;
        _percentChange1h = percentChange1h;
        _percentChange24h = percentChange24h;
        _percentChange7d = percentChange7d;
        _availableSupply = availableSupply;
        _totalSupply = totalSupply;
        _maxSupply = maxSupply;
        _lastUpdated = lastUpdated;
        _volume24h = volume24h;
    }

    public static List<CoinRateUI> convertList(ApiData apiData, String currency) {
        List<CoinRateUI> result = new ArrayList<>();
        Price price = Price.getEmpety();
        for (Datum datum : apiData.getData()) {

            //TODO: ALARM hardcode
            if (currency.compareTo("USD") == 0) {
                price = datum.getQuote().get("USD");
            } else if (currency.compareTo("EUR") == 0) {
                price = datum.getQuote().get("EUR");
            } else if (currency.compareTo("RUB") == 0) {
                price = datum.getQuote().get("RUB");
            }

            result.add(new CoinRateUI(currency,
                    datum.getId().toString(), datum.getName(), datum.getSymbol(), datum.getCmcRank(), price.getPrice(), price.getMarketCap(),
                    price.getPercentChange1h().round(new MathContext(2)).doubleValue(),
                    price.getPercentChange24h().round(new MathContext(2)).doubleValue(),
                    price.getPercentChange7d().round(new MathContext(2)).doubleValue(), datum.getCirculatingSupply(),
                    datum.getTotalSupply(), datum.getMaxSupply(), 0, price.getVolume24h()));
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoinRateUI that = (CoinRateUI) o;

        if (getRank() != that.getRank()) return false;
        if (Double.compare(that.getPercentChange1h(), getPercentChange1h()) != 0) return false;
        if (Double.compare(that.getPercentChange24h(), getPercentChange24h()) != 0) return false;
        if (Double.compare(that.getPercentChange7d(), getPercentChange7d()) != 0) return false;
        if (getLastUpdated() != that.getLastUpdated()) return false;
        if (getCurrency() != null ? !getCurrency().equals(that.getCurrency()) : that.getCurrency() != null)
            return false;
        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
            return false;
        if (getSymbol() != null ? !getSymbol().equals(that.getSymbol()) : that.getSymbol() != null)
            return false;
        if (getPrice() != null ? !getPrice().equals(that.getPrice()) : that.getPrice() != null)
            return false;
        if (getMarketCap() != null ? !getMarketCap().equals(that.getMarketCap()) : that.getMarketCap() != null)
            return false;
        if (getAvailableSupply() != null ? !getAvailableSupply().equals(that.getAvailableSupply()) : that.getAvailableSupply() != null)
            return false;
        if (getTotalSupply() != null ? !getTotalSupply().equals(that.getTotalSupply()) : that.getTotalSupply() != null)
            return false;
        if (getMaxSupply() != null ? !getMaxSupply().equals(that.getMaxSupply()) : that.getMaxSupply() != null)
            return false;
        return getVolume24h() != null ? getVolume24h().equals(that.getVolume24h()) : that.getVolume24h() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getCurrency() != null ? getCurrency().hashCode() : 0;
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getSymbol() != null ? getSymbol().hashCode() : 0);
        result = 31 * result + getRank();
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + (getMarketCap() != null ? getMarketCap().hashCode() : 0);
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
        result = 31 * result + (getVolume24h() != null ? getVolume24h().hashCode() : 0);
        return result;
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public String getSymbol() {
        return _symbol;
    }

    public int getRank() {
        return _rank;
    }

    public BigDecimal getVolume24h() {
        return _volume24h;
    }

    public String getUIVolume24() {
        return LocaleUtils.formatBigDecimal(getVolume24h());
    }

    public BigDecimal getMarketCap() {
        return _marketCap;
    }

    public String getUIMarketCap() {
        return LocaleUtils.formatBigDecimal(getMarketCap());
    }

    public BigDecimal getPrice() {
        return _price;
    }

    private int getColorPercent(double percent) {
        if (percent > 0.0) {
            return R.color.colorUp;
        } else {
            return R.color.colorDown;
        }
    }

    public String getUIPrice() {
        return LocaleUtils.formatBigDecimal(getPrice());
    }

    public String getURLImage() {
        return BuildConfig.IMG_URL + getSymbol().toLowerCase() + "@2x.png";
    }

    public double getPercentChange1h() {
        return _percentChange1h;
    }

    public double getPercentChange24h() {
        return _percentChange24h;
    }

    public double getPercentChange7d() {
        return _percentChange7d;
    }

    public int getColorPercentChange1h() {
        return getColorPercent(getPercentChange1h());
    }

    public int getColorPercentChange24h() {
        return getColorPercent(getPercentChange24h());

    }

    public int getColorPercentChange7d() {
        return getColorPercent(getPercentChange7d());

    }

    public BigDecimal getAvailableSupply() {
        return _availableSupply;
    }

    public BigDecimal getTotalSupply() {
        return _totalSupply;
    }

    public BigDecimal getMaxSupply() {
        return _maxSupply;
    }

    public int getLastUpdated() {
        return _lastUpdated;
    }

    public String getUIPercentChange1h() {
        return String.valueOf(getPercentChange1h());
    }

    public String getUIPercentChange24h() {
        return String.valueOf(getPercentChange24h());
    }

    public String getUIPercentChange7d() {
        return String.valueOf(getPercentChange7d());
    }

    public String getUIAvailableSupply() {
        return LocaleUtils.formatBigDecimal(getAvailableSupply());
    }

    public String getUITotalSupply() {
        return LocaleUtils.formatBigDecimal(getTotalSupply());
    }

    public String getUIMaxSupply() {
        return LocaleUtils.formatBigDecimal(getMaxSupply());
    }

    public String getUILastUpdated() {
        return String.valueOf(getLastUpdated());
    }

    public String getCurrency() {
        return _currency;
    }

    public String getUICurrency() {
        if (_currency.compareTo("USD") == 0) {
            return "\u0024";//"\uf155";
        }
        if (_currency.compareTo("RUB") == 0) {
            return "\u20BD";//"\uf155";
        }
        if (_currency.compareTo("EUR") == 0) {
            return "\u20AC";//"\uf155";
        }

        return _currency;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._currency);
        dest.writeString(this._id);
        dest.writeString(this._name);
        dest.writeString(this._symbol);
        dest.writeInt(this._rank);
        dest.writeSerializable(this._price);
        dest.writeSerializable(this._marketCap);
        dest.writeDouble(this._percentChange1h);
        dest.writeDouble(this._percentChange24h);
        dest.writeDouble(this._percentChange7d);
        dest.writeSerializable(this._availableSupply);
        dest.writeSerializable(this._totalSupply);
        dest.writeSerializable(this._maxSupply);
        dest.writeInt(this._lastUpdated);
        dest.writeSerializable(this._volume24h);
    }

    protected CoinRateUI(Parcel in) {
        this._currency = in.readString();
        this._id = in.readString();
        this._name = in.readString();
        this._symbol = in.readString();
        this._rank = in.readInt();
        this._price = (BigDecimal) in.readSerializable();
        this._marketCap = (BigDecimal) in.readSerializable();
        this._percentChange1h = in.readDouble();
        this._percentChange24h = in.readDouble();
        this._percentChange7d = in.readDouble();
        this._availableSupply = (BigDecimal) in.readSerializable();
        this._totalSupply = (BigDecimal) in.readSerializable();
        this._maxSupply = (BigDecimal) in.readSerializable();
        this._lastUpdated = in.readInt();
        this._volume24h = (BigDecimal) in.readSerializable();
    }

    public static final Creator<CoinRateUI> CREATOR = new Creator<CoinRateUI>() {
        @Override
        public CoinRateUI createFromParcel(Parcel source) {
            return new CoinRateUI(source);
        }

        @Override
        public CoinRateUI[] newArray(int size) {
            return new CoinRateUI[size];
        }
    };
}
