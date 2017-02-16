package com.nicrob64.bitcoinaddressmonitorwidget.service;

import com.nicrob64.bitcoinaddressmonitorwidget.model.event.BitcoinExchangeRateUpdatedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.util.AsyncExecutor;

import java.util.Map;

import info.blockchain.api.exchangerates.*;

/**
 * Created by Admin on 31/01/2017.
 */

public class BitcoinNetworkService {

    public static void getExchangeRates(){

        AsyncExecutor.create().execute(new AsyncExecutor.RunnableEx() {
            @Override
            public void run() throws Exception {
                try {
                    Map<String, Currency> map = ExchangeRates.getTicker();
                    EventBus.getDefault().post(new BitcoinExchangeRateUpdatedEvent(map));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

}
