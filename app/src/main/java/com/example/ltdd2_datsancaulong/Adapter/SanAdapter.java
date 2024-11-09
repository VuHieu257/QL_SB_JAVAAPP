package com.example.ltdd2_datsancaulong.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltdd2_datsancaulong.Activity.Admin.AdminDatSan;
import com.example.ltdd2_datsancaulong.Activity.Admin.ChiTietSan;
import com.example.ltdd2_datsancaulong.Activity.Admin.DanhSachSan;
import com.example.ltdd2_datsancaulong.ClickListtener.SanClickListener;
import com.example.ltdd2_datsancaulong.Holder.SanHolder;
import com.example.ltdd2_datsancaulong.Models.San;
import com.example.ltdd2_datsancaulong.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class SanAdapter extends RecyclerView.Adapter<SanHolder> {
    Context context;
    List<San> sanList;
    SanClickListener sanClickListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public SanAdapter(Context context, List<San> sanList, SanClickListener sanClickListener) {
        this.context = context;
        this.sanList = sanList;
        this.sanClickListener = sanClickListener;
    }

    @NonNull
    @Override
    public SanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_san, parent,false);
        return new SanHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanHolder holder, int position) {
        San san = sanList.get(position);
        holder.txtIDSan.setText(san.getIdSan());
        holder.txtTenSan.setText(san.getTenSan());
        //Picasso.get().load(san.getImageSan()).placeholder(R.drawable.imgsan1).into(holder.imgSan);
        if (TextUtils.isEmpty(san.getImageSan())) {
            holder.imgSan.setImageResource(R.drawable.imgsan1);
        } else {
            File folder = context.getCacheDir();
            File file = new File(folder, san.getImageSan());

            holder.imgSan.setImageURI(Uri.fromFile(file));
        }
        holder.cardViewSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("San");
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child(holder.txtIDSan.getText().toString()).removeValue();
                sanList.clear();
            }
        });
        holder.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChiTietSan.class);
                intent.putExtra("idSan", sanList.get(holder.getAdapterPosition()).getIdSan());
                intent.putExtra("tenSan", sanList.get(holder.getAdapterPosition()).getTenSan());
                intent.putExtra("imageSan", sanList.get(holder.getAdapterPosition()).getImageSan());
                sanList.clear();
                context.startActivity(intent);
            }
        });
        holder.cardViewSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AdminDatSan.class);
                intent.putExtra("tenSan", sanList.get(holder.getAdapterPosition()).getTenSan());
                intent.putExtra("imageSan", sanList.get(holder.getAdapterPosition()).getImageSan());
                sanList.clear();
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sanList.size();
    }
}
