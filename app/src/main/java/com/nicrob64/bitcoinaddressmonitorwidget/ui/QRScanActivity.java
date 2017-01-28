package com.nicrob64.bitcoinaddressmonitorwidget.ui;

import android.content.Intent;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.nicrob64.bitcoinaddressmonitorwidget.R;

public class QRScanActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {

    public static final String KEY_RESULT = "KEY_RESULT";
    public static final int REQUEST_QR_SCAN = 500;

    private QRCodeReaderView mQRReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);
        init();
    }

    private void init(){
        mQRReader = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mQRReader.setOnQRCodeReadListener(this);
        mQRReader.setBackCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mQRReader.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mQRReader.stopCamera();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_RESULT, text);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
