package com.nicrob64.bitcoinaddressmonitorwidget.model.event;

import com.nicrob64.bitcoinaddressmonitorwidget.model.BitcoinExchangeRate;

import java.util.ArrayList;

/**
 * Created by Admin on 13/02/2017.
 */

public class BitcoinExchangeRateUpdatedEvent {
    public ArrayList<BitcoinExchangeRate> exchangeRates;
    public BitcoinExchangeRateUpdatedEvent(ArrayList<BitcoinExchangeRate> exchangeRates){
        this.exchangeRates = exchangeRates;
    }
}
