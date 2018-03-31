package ru.divizdev.coinrate.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ru.divizdev.coinrate.R;
import ru.divizdev.coinrate.rates.LocaleUtils;

/**
 * Created by diviz on 08.02.2018.
 */

public class CoinRateUI implements Parcelable {

    final private CoinRateApi _coinRateApi;
    final private String _currency;

    public static List<CoinRateUI> convertList(List<CoinRateApi> coinRateApis, String currency) {
        List<CoinRateUI> result = new ArrayList<>();

        for (CoinRateApi coinRateApi : coinRateApis) {
            result.add(new CoinRateUI(coinRateApi, currency));
        }
        return result;
    }


    public CoinRateUI(CoinRateApi coinRateApi, String currency) {

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
        return LocaleUtils.formatBigDecimal(getVolume24h());
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
        return LocaleUtils.formatBigDecimal(getMarketCap());
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
            return R.color.colorUp;
        } else {
            return R.color.colorDown;
        }
    }

    public String getUIPrice() {
        return LocaleUtils.formatBigDecimal(getPrice());
    }

    public String getURLImage() {
        return "http://divizdev.ru/CoinRate/color/" + getSymbol().toLowerCase() + "@2x.png";
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


    protected CoinRateUI(Parcel parcel) {
        _coinRateApi = CoinRateApi.CREATOR.createFromParcel(parcel);
        _currency = parcel.readString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoinRateUI coinRateUI = (CoinRateUI) o;

        if (_coinRateApi != null ? !_coinRateApi.equals(coinRateUI._coinRateApi) : coinRateUI._coinRateApi != null)
            return false;
        return getCurrency() != null ? getCurrency().equals(coinRateUI.getCurrency()) : coinRateUI.getCurrency() == null;
    }

    @Override
    public int hashCode() {
        int result = _coinRateApi != null ? _coinRateApi.hashCode() : 0;
        result = 31 * result + (getCurrency() != null ? getCurrency().hashCode() : 0);
        return result;
    }

    public static final Creator<CoinRateUI> CREATOR = new Creator<CoinRateUI>() {
        @Override
        public CoinRateUI createFromParcel(Parcel parcel) {
            return new CoinRateUI(parcel);
        }

        @Override
        public CoinRateUI[] newArray(int size) {
            return new CoinRateUI[size];
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
