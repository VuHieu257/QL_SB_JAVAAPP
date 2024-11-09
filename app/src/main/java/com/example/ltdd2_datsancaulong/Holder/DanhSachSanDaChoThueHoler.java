package com.example.ltdd2_datsancaulong.Holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltdd2_datsancaulong.R;

public class DanhSachSanDaChoThueHoler extends RecyclerView.ViewHolder {
    public CardView cardViewSanDaThue;
    public TextView txtIDSanThue, txtTenKhachHang, txtTenSan, txtGioThue;
    public DanhSachSanDaChoThueHoler(@NonNull View itemView) {
        super(itemView);
        cardViewSanDaThue = itemView.findViewById(R.id.cardViewSanDaThue);
        txtIDSanThue = itemView.findViewById(R.id.txtIDSanThue);
        txtTenKhachHang = itemView.findViewById(R.id.txtTenKhachHang);
        txtTenSan = itemView.findViewById(R.id.txtTenSan);
        txtGioThue = itemView.findViewById(R.id.txtGioThue);
    }
}
