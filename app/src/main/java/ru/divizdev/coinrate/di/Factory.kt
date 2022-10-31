package ru.divizdev.coinrate.di

import ru.divizdev.coinrate.utils.Config

object Factory {
    @JvmStatic
    val config: Config = Config()
}