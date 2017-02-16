package com.nicrob64.bitcoinaddressmonitorwidget.model.event;

import java.util.Map;

import info.blockchain.api.exchangerates.Currency;

/**
 * Created by Admin on 13/02/2017.
 */

public class BitcoinExchangeRateUpdatedEvent {
    public Map<String, Currency> exchangeRates;
    public BitcoinExchangeRateUpdatedEvent(Map<String, Currency> exchangeRates){
        this.exchangeRates = exchangeRates;
    }
}
