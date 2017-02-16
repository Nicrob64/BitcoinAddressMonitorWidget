package com.nicrob64.bitcoinaddressmonitorwidget.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.SupportActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.nicrob64.bitcoinaddressmonitorwidget.R;
import com.nicrob64.bitcoinaddressmonitorwidget.model.XpubEntry;
import com.nicrob64.bitcoinaddressmonitorwidget.ui.adapter.XpubAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Admin on 16/02/2017.
 */

public class HelpAddAddressActivity extends AppCompatActivity {

    RecyclerView mXpubRecyclerView;
    XpubAdapter mXpubAdapter;
    ArrayList<XpubEntry> xpubEntries = new ArrayList<>();
    Toolbar mToolbar;

    public static void start(Context context) {
        Intent starter = new Intent(context, HelpAddAddressActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_add_address);
        init();
    }

    private void init(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.help_address_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mXpubRecyclerView = (RecyclerView) findViewById(R.id.xpub_recycler_view);

        Gson gson = new Gson();
        InputStream inputStream = getResources().openRawResource(R.raw.xpub);
        String json = readJsonFile(inputStream);
        xpubEntries = gson.fromJson(new JsonParser().parse(json), new TypeToken<ArrayList<XpubEntry>>(){}.getType());
        mXpubAdapter = new XpubAdapter(xpubEntries);
        mXpubRecyclerView.setAdapter(mXpubAdapter);
        mXpubRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        mXpubRecyclerView.setNestedScrollingEnabled(false);
        mXpubRecyclerView.setFocusable(false);
        mXpubAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String readJsonFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte bufferByte[] = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(bufferByte)) != -1) {
                outputStream.write(bufferByte, 0, length);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toString();
    }
}
