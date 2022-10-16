package ru.divizdev.coinrate.utils

import ru.divizdev.coinrate.BuildConfig

class Config {
    private var _baseURL: String? = null
    fun setBaseURL(baseURL: String?) {
        _baseURL = baseURL
    }

    val baseUrl: String
        get() = if (_baseURL == null) {
            BuildConfig.API2_URL
        } else {
            _baseURL!!
        }
}