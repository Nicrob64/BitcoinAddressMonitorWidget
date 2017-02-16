package com.nicrob64.bitcoinaddressmonitorwidget.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.nicrob64.bitcoinaddressmonitorwidget.R;
import com.nicrob64.bitcoinaddressmonitorwidget.model.XpubEntry;

import java.util.ArrayList;

/**
 * Created by Admin on 16/02/2017.
 */

public class XpubAdapter extends RecyclerView.Adapter<XpubAdapter.XpubHolder> {

    private ArrayList<XpubEntry> mItems = new ArrayList<>();

    public XpubAdapter(ArrayList<XpubEntry> entries){
        this.mItems = entries;
    }

    @Override
    public XpubHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(XpubHolder.sLayoutId, parent, false);
        return new XpubHolder(v);
    }

    @Override
    public void onBindViewHolder(XpubHolder holder, int position) {
        holder.titleTextView.setText(mItems.get(position).title);
        holder.subtitleTextView.setText(mItems.get(position).description);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class XpubHolder extends RecyclerView.ViewHolder{
        public static final int sLayoutId = R.layout.xpub_layout_row;

        TextView titleTextView;
        TextView subtitleTextView;

        public XpubHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title_textview);
            subtitleTextView = (TextView) itemView.findViewById(R.id.subtitle_textview);
        }
    }
}
