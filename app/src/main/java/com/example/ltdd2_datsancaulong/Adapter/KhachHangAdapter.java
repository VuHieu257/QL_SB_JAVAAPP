package com.example.ltdd2_datsancaulong.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltdd2_datsancaulong.Activity.Admin.ChiTietKhachHang;
import com.example.ltdd2_datsancaulong.ClickListtener.KhachHangClickListenner;
import com.example.ltdd2_datsancaulong.Holder.KhachHangHolder;
import com.example.ltdd2_datsancaulong.Models.KhachHang;
import com.example.ltdd2_datsancaulong.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangHolder> {
    Context context;
    List<KhachHang> khachHangList;
    KhachHangClickListenner listenner;

    public KhachHangAdapter(Context context, List<KhachHang> khachHangList, KhachHangClickListenner listenner) {
        this.context = context;
        this.khachHangList = khachHangList;
        this.listenner = listenner;
    }

    @NonNull
    @Override
    public KhachHangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_khach_hang, parent, false);
        return new KhachHangHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhachHangHolder holder, @SuppressLint("RecyclerView") int position) {
        KhachHang khachHang = khachHangList.get(position);
        if (TextUtils.isEmpty(khachHang.getImageKhachHang())) {
            holder.imageKhachHang.setImageResource(R.drawable.imgmessi);
        } else {
            try {
                File folder = context.getCacheDir();
                File file = new File(folder, khachHang.getImageKhachHang());

                holder.imageKhachHang.setImageURI(Uri.fromFile(file));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        holder.txtHotenNguoiThue.setText(khachHang.getTenKhachHang());
        holder.txtSDTNguoiThue.setText(khachHang.getSoDienThoai());
        holder.txtPasswordNguoiThue.setText(khachHang.getPassword());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChiTietKhachHang.class);
                intent.putExtra("idKhachHang", khachHangList.get(position).getIdKhachHang());
                intent.putExtra("tenKhachHang", khachHangList.get(holder.getAdapterPosition()).getTenKhachHang());
                intent.putExtra("soDienThoai", khachHangList.get(holder.getAdapterPosition()).getSoDienThoai());
                intent.putExtra("password", khachHangList.get(holder.getAdapterPosition()).getPassword());
                intent.putExtra("conFirm", khachHangList.get(holder.getAdapterPosition()).getConFirm());
                intent.putExtra("imageKhachHang", khachHangList.get(holder.getAdapterPosition()).getImageKhachHang());
                context.startActivity(intent);
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listenner.OnItemLongClick(khachHangList.get(holder.getAdapterPosition()));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return khachHangList.size();
    }
}
