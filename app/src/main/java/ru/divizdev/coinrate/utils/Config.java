package ru.divizdev.coinrate.utils;

import ru.divizdev.coinrate.BuildConfig;

public class Config {

    private String _baseURL = null;

    public void setBaseURL(String baseURL) {
        _baseURL = baseURL;
    }

    public String getBaseUrl() {
        if (_baseURL == null) {
            return BuildConfig.API2_URL;
        } else {
            return _baseURL;
        }
    }

}
