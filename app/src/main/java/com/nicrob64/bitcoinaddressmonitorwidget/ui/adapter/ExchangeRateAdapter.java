package com.nicrob64.bitcoinaddressmonitorwidget.ui.adapter;

import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.nicrob64.bitcoinaddressmonitorwidget.R;
import com.nicrob64.bitcoinaddressmonitorwidget.model.BitcoinExchangeRate;

import java.util.ArrayList;

/**
 * Created by Admin on 13/02/2017.
 */

public class ExchangeRateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<BitcoinExchangeRate> items;
    ItemCheckListener listener;

    public interface ItemCheckListener{
        void onItemCheck(int item, boolean checked);
    }

    public ExchangeRateAdapter(ArrayList<BitcoinExchangeRate> exchangeRates, ItemCheckListener listener){
        this.items = exchangeRates;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(ExchangeRateHolder.sLayoutId, parent, false);
        return new ExchangeRateHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ExchangeRateHolder evh = (ExchangeRateHolder) holder;
        evh.checkBox.setText(items.get(position).shortCode);
        evh.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                listener.onItemCheck(holder.getAdapterPosition(), b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ExchangeRateHolder extends RecyclerView.ViewHolder{
        public static final int sLayoutId = R.layout.checkbox_row;
        CheckBox checkBox;

        public ExchangeRateHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }

}
