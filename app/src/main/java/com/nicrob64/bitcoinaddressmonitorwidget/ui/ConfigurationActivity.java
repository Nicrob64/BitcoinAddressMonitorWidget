package com.nicrob64.bitcoinaddressmonitorwidget.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.nicrob64.bitcoinaddressmonitorwidget.R;
import com.nicrob64.bitcoinaddressmonitorwidget.ui.adapter.AddressAdapter;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationActivity extends AppCompatActivity implements View.OnClickListener, AddressAdapter.AddressAdapterListener {

    List<String> mAddressList = new ArrayList<>();
    Button mScanAnotherCodeButton;
    RecyclerView mAddressListRecyclerView;
    AddressAdapter mAddressAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        setResult(RESULT_CANCELED);
        init();
    }

    private void init(){
        mScanAnotherCodeButton = (Button) findViewById(R.id.add_another_address_button);
        mScanAnotherCodeButton.setOnClickListener(this);
        mAddressListRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAddressListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAddressAdapter = new AddressAdapter(mAddressList, this);
        mAddressListRecyclerView.setAdapter(mAddressAdapter);
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
}
