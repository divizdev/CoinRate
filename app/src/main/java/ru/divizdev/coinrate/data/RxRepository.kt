package ru.divizdev.coinrate.data;

import java.util.List;

import io.reactivex.Observable;
import ru.divizdev.coinrate.presentation.entities.CoinRateUI;

public interface RxRepository {

    Observable<List<CoinRateUI>> getData(String currency, boolean isForced);

}
