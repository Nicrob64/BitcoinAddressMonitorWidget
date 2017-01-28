package com.nicrob64.bitcoinaddressmonitorwidget.model;

import java.io.Serializable;

/**
 * Created by Admin on 28/01/2017.
 */

public class BitcoinAddress implements Serializable{
    private String address = "";
    private double balance = 0.0;

    public double getBalance(){
        return balance;
    }
}
