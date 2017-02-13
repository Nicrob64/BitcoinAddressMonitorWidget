package com.nicrob64.bitcoinaddressmonitorwidget.model;

/**
 * Created by Admin on 31/01/2017.
 */

public class BitcoinExchangeRate {
    public String shortCode;
    public double exchangeRate;
    public BitcoinExchangeRate(String code, double exchangeRate){
        this.shortCode = code;
        this.exchangeRate = exchangeRate;
    }
}
