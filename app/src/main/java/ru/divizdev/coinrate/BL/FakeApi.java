package ru.divizdev.coinrate.BL;

import java.util.ArrayList;
import java.util.List;

import ru.divizdev.coinrate.Entities.CoinRate;

public class FakeApi {

    static public List<CoinRate> getListCoinRate() {
        ArrayList<CoinRate> result = new ArrayList<>();

        result.add(new CoinRate("bitcoin", "Bitcoin", "BTC", 1, 15103.00, -2.02, -9.89, 11.63, 0,0));
        result.add(new CoinRate("ethereum", "Ethereum", "ETH", 2, 1088.6, -5.0, -2.22, 42.38,  0, 0));
        result.add(new CoinRate("bitcoin", "Bitcoin", "BTC", 3, 15103.00, -2.02, -9.89, 11.63, 0,0));
        result.add(new CoinRate("bitcoin", "Bitcoin", "BTC", 4, 15103.00, -2.02, -9.89, 11.63, 0,0));
        result.add(new CoinRate("bitcoin", "Bitcoin", "BTC", 5, 15103.00, -2.02, -9.89, 11.63, 0,0));
        result.add(new CoinRate("bitcoin", "Bitcoin", "BTC", 6, 15103.00, -2.02, -9.89, 11.63, 0,0));

        return result;
    }
}
