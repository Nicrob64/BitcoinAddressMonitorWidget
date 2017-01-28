package com.nicrob64.bitcoinaddressmonitorwidget.ui;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.nicrob64.bitcoinaddressmonitorwidget.R;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

public class QRScanActivity extends AppCompatActivity implements QRDataListener {

    public static final String KEY_RESULT = "KEY_RESULT";
    public static final int REQUEST_QR_SCAN = 500;

    private SurfaceView mSurfaceView;
    private QREader qrEader;
    private RelativeLayout mNoPermissionLayout;
    private Button mGrantPermissionsButton;
    public boolean isPermanentlyDenied = false;
    private PermissionListener mPermissionListener = new PermissionListener() {

        @Override
        public void onPermissionGranted(PermissionGrantedResponse response) {
            mNoPermissionLayout.setVisibility(View.GONE);
            initQRReader();
        }

        @Override
        public void onPermissionDenied(PermissionDeniedResponse response) {
            showNoCameraView();
            isPermanentlyDenied = response.isPermanentlyDenied();
        }

        @Override
        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
            token.continuePermissionRequest();
            //token.cancelPermissionRequest();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);
        init();
    }

    private void init(){
        mSurfaceView = (SurfaceView) findViewById(R.id.camera_view);
        mNoPermissionLayout = (RelativeLayout) findViewById(R.id.no_camera_permission_layout);
        mGrantPermissionsButton = (Button) findViewById(R.id.grant_permissions_button);
        mGrantPermissionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPermanentlyDenied) {
                    checkPermission();
                }else{
                    showAppSettingsScreen();
                }
            }
        });
        checkPermission();
    }

    private void checkPermission(){
        mNoPermissionLayout.setVisibility(View.VISIBLE);
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(mPermissionListener)
                .check();
    }

    private void showAppSettingsScreen(){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void initQRReader(){
        qrEader = new QREader.Builder(this, mSurfaceView, this).facing(QREader.BACK_CAM)
                .enableAutofocus(true)
                .height(mSurfaceView.getHeight())
                .width(mSurfaceView.getWidth())
                .build();
        if(!qrEader.isCameraRunning()) {
            qrEader.initAndStart(mSurfaceView);
        }
    }

    private void showNoCameraView(){
        mNoPermissionLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(qrEader != null) {
            if(!qrEader.isCameraRunning()) {
                qrEader.initAndStart(mSurfaceView);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(qrEader != null) {
            qrEader.releaseAndCleanup();
        }
    }

    @Override
    public void onDetected(String text) {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_RESULT, text);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
