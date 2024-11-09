package com.example.ltdd2_datsancaulong.Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltdd2_datsancaulong.R;

public class SanUserHolder extends RecyclerView.ViewHolder {
    public CardView cardViewSan;
    public TextView txtTenSan;
    public ImageView imgSan;

    public SanUserHolder(@NonNull View itemView) {
        super(itemView);
        cardViewSan = itemView.findViewById(R.id.cardViewSan);
        imgSan = itemView.findViewById(R.id.imgSan);
        txtTenSan = itemView.findViewById(R.id.txtTenSan);
    }
}
