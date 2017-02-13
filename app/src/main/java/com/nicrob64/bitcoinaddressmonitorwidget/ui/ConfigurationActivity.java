package com.nicrob64.bitcoinaddressmonitorwidget.ui;

import android.appwidget.AppWidgetManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.nicrob64.bitcoinaddressmonitorwidget.R;
import com.nicrob64.bitcoinaddressmonitorwidget.model.BitcoinExchangeRate;
import com.nicrob64.bitcoinaddressmonitorwidget.model.event.BitcoinExchangeRateUpdatedEvent;
import com.nicrob64.bitcoinaddressmonitorwidget.service.BitcoinNetworkService;
import com.nicrob64.bitcoinaddressmonitorwidget.ui.adapter.AddressAdapter;
import com.nicrob64.bitcoinaddressmonitorwidget.ui.adapter.ExchangeRateAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationActivity extends AppCompatActivity implements View.OnClickListener, AddressAdapter.AddressAdapterListener, ExchangeRateAdapter.ItemCheckListener {

    List<String> mAddressList = new ArrayList<>();
    Button mScanAnotherCodeButton;
    RecyclerView mAddressListRecyclerView;
    RecyclerView mExchangeRateRecyclerView;
    AddressAdapter mAddressAdapter;
    ExchangeRateAdapter mExchangeRateAdapter;
    ArrayList<BitcoinExchangeRate> mExhangeRates = new ArrayList<>();
    ArrayList<BitcoinExchangeRate> mSelectedExchangeRates = new ArrayList<>();
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        setResult(RESULT_CANCELED);
        init();
    }

    private void init(){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        mScanAnotherCodeButton = (Button) findViewById(R.id.add_another_address_button);
        mScanAnotherCodeButton.setOnClickListener(this);
        mAddressListRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAddressListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAddressAdapter = new AddressAdapter(mAddressList, this);
        mAddressListRecyclerView.setAdapter(mAddressAdapter);

        mExchangeRateRecyclerView = (RecyclerView) findViewById(R.id.exchange_rate_recycler_view);
        mExchangeRateAdapter = new ExchangeRateAdapter(mExhangeRates, this);
        mExchangeRateRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mExchangeRateRecyclerView.setAdapter(mExchangeRateAdapter);
        BitcoinNetworkService.getExchangeRates();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_another_address_button:
                Intent scanIntent = new Intent(this, QRScanActivity.class);
                startActivityForResult(scanIntent, QRScanActivity.REQUEST_QR_SCAN);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }
        switch (requestCode){
            case QRScanActivity.REQUEST_QR_SCAN:
                String scannedAddress = data.getStringExtra(QRScanActivity.KEY_RESULT);
                if(scannedAddress.startsWith("bitcoin:")){
                    scannedAddress = scannedAddress.substring("bitcoin:".length());
                }
                if(!mAddressList.contains(scannedAddress)) {
                    mAddressList.add(scannedAddress);
                    mAddressAdapter.notifyItemInserted(mAddressList.size()-1);
                }else{
                    showErrorDialog(getString(R.string.whoops), getString(R.string.already_added_address_s, scannedAddress));
                }
                break;
        }
    }

    private void showErrorDialog(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
            .setTitle(title)
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            })
            .show();
    }

    @Override
    public void onDelete(int position) {
        mAddressList.remove(position);
        mAddressAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onClick(int position) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBitcoinExchangeRateUpdatedEvent(BitcoinExchangeRateUpdatedEvent event){
        int oldSize = mExhangeRates.size();
        for(BitcoinExchangeRate exchangeRate: event.exchangeRates){
            if(!mExhangeRates.contains(exchangeRate)){
                mExhangeRates.add(exchangeRate);
            }
        }
        int newSize = mExhangeRates.size();
        if(oldSize == 0) {
            mExchangeRateAdapter.notifyDataSetChanged();
        }else{
            mExchangeRateAdapter.notifyItemRangeInserted(oldSize, newSize);
        }
    }

    @Override
    public void onItemCheck(int item, boolean checked) {
        BitcoinExchangeRate rate = mExhangeRates.get(item);
        if(checked){
            if(!mSelectedExchangeRates.contains(rate)){
                mSelectedExchangeRates.add(rate);
            }
        }else{
            if(mSelectedExchangeRates.contains(rate)){
                mSelectedExchangeRates.remove(rate);
            }
        }
    }
}
