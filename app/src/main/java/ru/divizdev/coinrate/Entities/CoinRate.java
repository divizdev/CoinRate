package ru.divizdev.coinrate.Entities;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import ru.divizdev.coinrate.rates.LocaleUtils;

/**
 * Created by diviz on 08.02.2018.
 */

public class CoinRate implements Parcelable {

    final private CoinRateApi _coinRateApi;
    final private String _currency;

    public static List<CoinRate> convertList(List<CoinRateApi> coinRateApis, String currency) {
        List<CoinRate> result = new ArrayList<>();

        for (CoinRateApi coinRateApi : coinRateApis) {
            result.add(new CoinRate(coinRateApi, currency));
        }
        return result;
    }


    public CoinRate(CoinRateApi coinRateApi, String currency) {

        _coinRateApi = coinRateApi;
        _currency = currency;
    }


    public String getId() {
        return _coinRateApi.getId();
    }

    public String getName() {
        return _coinRateApi.getName();
    }

    public String getSymbol() {
        return _coinRateApi.getSymbol();
    }

    public int getRank() {
        return _coinRateApi.getRank();
    }

    public BigDecimal getVolume24h() {
        BigDecimal result;

        if (getCurrency().compareTo("USD") != 0) {

            result = _coinRateApi.getVolume24hAlternate();
        } else {
            result = _coinRateApi.getVolume24hUsd();
        }

        if (result == null) {
            return BigDecimal.ZERO;
        }
        return result;
    }

    public String getUIVolume24() {
        return formatBigDecimal(getVolume24h());
    }


    public BigDecimal getMarketCap() {
        BigDecimal result;

        if (getCurrency().compareTo("USD") != 0) {

            result = _coinRateApi.getMarketCapAlternate();
        } else {
            result = _coinRateApi.getMarketCapUsd();
        }
        if (result == null) {
            return BigDecimal.ZERO;
        }
        return result;
    }

    public String getUIMarketCap() {
        return formatBigDecimal(getMarketCap());
    }

    public BigDecimal getPrice() {

        BigDecimal result;

        if (getCurrency().compareTo("USD") != 0) {
            result = _coinRateApi.getPriceAlternate();
        } else {
            result = _coinRateApi.getPriceUsd();
        }

        if (result == null) {
            return BigDecimal.ZERO;
        }
        return result;
    }

    private int getColorPercent(double percent) {
        if (percent > 0.0) {
            return Color.GREEN;
        } else {
            return Color.RED;
        }
    }

    private String formatBigDecimal(BigDecimal decimal) {
        BigDecimal decimalCopy = decimal.setScale(LocaleUtils.getCurrentScale(), RoundingMode.HALF_DOWN);
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(LocaleUtils.getCurrentLocale());
        df.setMinimumFractionDigits(2);
        df.setGroupingUsed(true);
        return df.format(decimalCopy);
    }

    public String getUIPrice() {
        return formatBigDecimal(getPrice());
    }

    public double getPercentChange1h() {
        return _coinRateApi.getPercentChange1h();
    }

    public double getPercentChange24h() {
        return _coinRateApi.getPercentChange24h();
    }

    public double getPercentChange7d() {
        return _coinRateApi.getPercentChange7d();
    }

    public BigDecimal getAvailableSupply() {
        if (_coinRateApi.getAvailableSupply() == null) {
            return BigDecimal.ZERO;
        }
        return _coinRateApi.getAvailableSupply();
    }

    public BigDecimal getTotalSupply() {
        if (_coinRateApi.getTotalSupply() == null) {
            return BigDecimal.ZERO;
        }
        return _coinRateApi.getTotalSupply();
    }

    public BigDecimal getMaxSupply() {
        if (_coinRateApi.getMaxSupply() == null) {
            return BigDecimal.ZERO;
        }
        return _coinRateApi.getMaxSupply();
    }

    public int getLastUpdated() {
        return _coinRateApi.getLastUpdated();
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
        return formatBigDecimal(getAvailableSupply());
    }

    public String getUITotalSupply() {
        return formatBigDecimal(getTotalSupply());
    }

    public String getUIMaxSupply() {
        return formatBigDecimal(getMaxSupply());
    }

    public String getUILastUpdated() {
        return String.valueOf(getLastUpdated());
    }


    public String getCurrency() {
        return _currency;
    }

    protected CoinRate(Parcel parcel) {
        _coinRateApi = CoinRateApi.CREATOR.createFromParcel(parcel);
        _currency = parcel.readString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoinRate coinRate = (CoinRate) o;

        if (_coinRateApi != null ? !_coinRateApi.equals(coinRate._coinRateApi) : coinRate._coinRateApi != null)
            return false;
        return getCurrency() != null ? getCurrency().equals(coinRate.getCurrency()) : coinRate.getCurrency() == null;
    }

    @Override
    public int hashCode() {
        int result = _coinRateApi != null ? _coinRateApi.hashCode() : 0;
        result = 31 * result + (getCurrency() != null ? getCurrency().hashCode() : 0);
        return result;
    }

    public static final Creator<CoinRate> CREATOR = new Creator<CoinRate>() {
        @Override
        public CoinRate createFromParcel(Parcel parcel) {
            return new CoinRate(parcel);
        }

        @Override
        public CoinRate[] newArray(int size) {
            return new CoinRate[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        _coinRateApi.writeToParcel(parcel, i);
        parcel.writeString(getCurrency());

    }
}
