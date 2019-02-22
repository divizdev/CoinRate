package ru.divizdev.coinrate.presentation.coinRateList.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import io.reactivex.Observable;
import ru.divizdev.coinrate.data.ManagerSettings;
import ru.divizdev.coinrate.data.RxRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public final class CoinRateListPresenterTest {

    @Mock
    CoinRateListView _coinRateListView;

    @Mock
    RxRepository _coinRateRepository;

    @Mock
    ManagerSettings _managerSettings;

    CoinRateListPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(_managerSettings.getCurCurrency()).thenReturn("USD");

        when(_coinRateRepository.getData("USD", false)).thenReturn(Observable.create(emitter -> emitter.onNext(new ArrayList<>())));
        when(_coinRateRepository.getData("RUB", false)).thenReturn(Observable.create(emitter -> emitter.onNext(new ArrayList<>())));
        presenter = new CoinRateListPresenter(_managerSettings, _coinRateRepository);
        presenter.attachView(_coinRateListView);

    }

    @Test
    public void setCurrency() {
        presenter.setCurrency("RUB");
        verify(_managerSettings).setCurCurrency("RUB");
//        verify(_coinRateListView).showLoadingProgress(true);
    }
}