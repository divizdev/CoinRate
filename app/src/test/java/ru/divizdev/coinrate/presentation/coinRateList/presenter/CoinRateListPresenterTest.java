package ru.divizdev.coinrate.presentation.coinRateList.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ru.divizdev.coinrate.data.CoinRateRepository;
import ru.divizdev.coinrate.data.ManagerSettings;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public final class CoinRateListPresenterTest {

    @Mock
    CoinRateListView _coinRateListView;

    @Mock
    CoinRateListView$$State _coinRateListView$$State;

    @Mock
    CoinRateRepository _coinRateRepository;

    @Mock
    ManagerSettings _managerSettings;

    CoinRateListPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(_managerSettings.getCurCurrency()).thenReturn("USD");

        presenter = new CoinRateListPresenter(_coinRateRepository, _managerSettings);
        presenter.attachView(_coinRateListView);
        presenter.setViewState(_coinRateListView$$State);
    }

    @Test
    public void setCurrency() {
        presenter.setCurrency("RUB");
        verify(_coinRateListView$$State).showLoadingProgress(true);
    }
}