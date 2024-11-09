package com.example.ltdd2_datsancaulong.Holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltdd2_datsancaulong.R;

public class SanHolder extends RecyclerView.ViewHolder {
    public CardView cardViewSan;
    public ImageView imgSan;
    public TextView txtIDSan, txtTenSan;
    public Button btnXoa, btnSua;
    public SanHolder(@NonNull View itemView) {
        super(itemView);
        cardViewSan = itemView.findViewById(R.id.cardViewSan);
        imgSan = itemView.findViewById(R.id.imgSan);
        txtIDSan = itemView.findViewById(R.id.txtIDSan);
        txtTenSan = itemView.findViewById(R.id.txtTenSan);
        btnXoa = itemView.findViewById(R.id.btnXoa);
        btnSua = itemView.findViewById(R.id.btnSua);
    }
}
