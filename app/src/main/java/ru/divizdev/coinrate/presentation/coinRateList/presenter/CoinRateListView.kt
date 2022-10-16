package ru.divizdev.coinrate.presentation.coinRateList.presenter;


import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.divizdev.coinrate.presentation.entities.CoinRateUI;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface CoinRateListView extends MvpView {
    void showLoadingProgress(Boolean isView);

    void showCoinRateList(List<CoinRateUI> list);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showErrorLoading();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showDialogAbout();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void navToDetail(CoinRateUI coinRateUI);
}
