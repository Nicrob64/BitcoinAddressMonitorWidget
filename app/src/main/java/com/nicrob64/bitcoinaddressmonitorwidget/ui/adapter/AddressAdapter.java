package com.nicrob64.bitcoinaddressmonitorwidget.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nicrob64.bitcoinaddressmonitorwidget.R;

import java.util.List;

/**
 * Created by Admin on 28/01/2017.
 */

public class AddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<String> mItems;
    AddressAdapterListener mListener;

    public AddressAdapter(List<String> items, AddressAdapterListener listener){
        mItems = items;
        mListener = listener;
    }

    public interface AddressAdapterListener{
        void onDelete(int position);
        void onClick(int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(AddressViewHolder.sLayoutId, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        String item = mItems.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mListener.onClick(holder.getAdapterPosition());
            }
        });
        if(holder instanceof AddressViewHolder){
            ((AddressViewHolder) holder).addressTextView.setText(item);
            ((AddressViewHolder) holder).deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onDelete(holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder{
        public static final int sLayoutId = R.layout.address_row;

        TextView addressTextView;
        ImageView deleteImageView;

        public AddressViewHolder(View itemView) {
            super(itemView);
            addressTextView = (TextView) itemView.findViewById(R.id.address_row_textview);
            deleteImageView = (ImageView) itemView.findViewById(R.id.address_row_delete_imageview);
        }
    }
}
