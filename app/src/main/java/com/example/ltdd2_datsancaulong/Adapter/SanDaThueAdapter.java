package com.example.ltdd2_datsancaulong.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltdd2_datsancaulong.ClickListtener.DanhSachSanDaThueClickListener;
import com.example.ltdd2_datsancaulong.Holder.DanhSachSanDaChoThueHoler;
import com.example.ltdd2_datsancaulong.Models.DatSan;
import com.example.ltdd2_datsancaulong.R;

import java.util.List;

public class SanDaThueAdapter extends RecyclerView.Adapter<DanhSachSanDaChoThueHoler> {
    Context context;
    List<DatSan> datSanList;
    DanhSachSanDaThueClickListener listener;

    public SanDaThueAdapter(Context context, List<DatSan> datSanList, DanhSachSanDaThueClickListener listener) {
        this.context = context;
        this.datSanList = datSanList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DanhSachSanDaChoThueHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_danh_sach_san_da_dat, parent, false);
        return new DanhSachSanDaChoThueHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhSachSanDaChoThueHoler holder, int position) {
        DatSan datSan = datSanList.get(position);
        holder.txtIDSanThue.setText(datSan.getIdDatSan());
        holder.txtTenKhachHang.setText(datSan.getTenKhachHang());
        holder.txtTenSan.setText(datSan.getTenSan());
        holder.txtGioThue.setText(datSan.getKhungGio());

        holder.cardViewSanDaThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemcClick(datSanList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return datSanList.size();
    }
}
