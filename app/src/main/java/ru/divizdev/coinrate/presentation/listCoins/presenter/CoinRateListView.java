package ru.divizdev.coinrate.presentation.listCoins.presenter;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.divizdev.coinrate.entities.CoinRateUI;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface CoinRateListView extends MvpView {
    void showLoadingProgress(Boolean isView);

    void showCoinRateList(List<CoinRateUI> list);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showErrorLoading();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showDialogAbout();

}
