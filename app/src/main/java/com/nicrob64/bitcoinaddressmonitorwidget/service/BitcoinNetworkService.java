package com.nicrob64.bitcoinaddressmonitorwidget.service;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.nicrob64.bitcoinaddressmonitorwidget.model.BitcoinExchangeRate;
import com.nicrob64.bitcoinaddressmonitorwidget.model.event.BitcoinExchangeRateUpdatedEvent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Admin on 31/01/2017.
 */

public class BitcoinNetworkService {

    public static void getExchangeRates(){
        AndroidNetworking.get("http://btc.blockr.io/api/v1/exchangerate/current")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<BitcoinExchangeRate> exchangeRates = new ArrayList<>();
                       try {
                           JSONObject rates = response.getJSONArray("data").getJSONObject(0).getJSONObject("rates");
                           Iterator<String> keys = rates.keys();
                           while (keys.hasNext()){
                               String key = keys.next();
                               double value = rates.getDouble(key);
                               exchangeRates.add(new BitcoinExchangeRate(key, value));
                           }
                           EventBus.getDefault().post(new BitcoinExchangeRateUpdatedEvent(exchangeRates));
                        }catch (JSONException e){
                           e.printStackTrace();
                       }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

}
