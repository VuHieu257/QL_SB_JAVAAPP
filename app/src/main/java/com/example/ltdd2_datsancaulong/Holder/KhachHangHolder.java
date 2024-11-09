package com.example.ltdd2_datsancaulong.Holder;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltdd2_datsancaulong.R;

public class KhachHangHolder extends RecyclerView.ViewHolder {
    public CardView cardView;
    public TextView txtHotenNguoiThue, txtSDTNguoiThue, txtPasswordNguoiThue;
    public ImageView imageKhachHang;
    public KhachHangHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.cardView);
        txtHotenNguoiThue = itemView.findViewById(R.id.txtHotenNguoiThue);
        txtSDTNguoiThue = itemView.findViewById(R.id.txtSDTNguoiThue);
        txtPasswordNguoiThue = itemView.findViewById(R.id.txtPasswordNguoiThue);
        imageKhachHang = itemView.findViewById(R.id.imgAvatar);
    }
}
