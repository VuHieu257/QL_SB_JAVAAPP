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
import com.example.ltdd2_datsancaulong.Activity.User.UserDatSan;
import com.example.ltdd2_datsancaulong.ClickListtener.SanClickListener;
import com.example.ltdd2_datsancaulong.Holder.SanUserHolder;
import com.example.ltdd2_datsancaulong.Models.San;
import com.example.ltdd2_datsancaulong.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SanUserAdpter extends RecyclerView.Adapter<SanUserHolder> {
    Context context;
    List<San> sanList;
    SanClickListener listener;

    public SanUserAdpter(Context context, List<San> sanList, SanClickListener listener) {
        this.context = context;
        this.sanList = sanList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SanUserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_san_user, parent, false);
        return new SanUserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanUserHolder holder, int position) {
        San san = sanList.get(position);
        holder.txtTenSan.setText(san.getTenSan());
//        Picasso.get().load(san.getImageSan()).placeholder(R.drawable.imgsan2).into(holder.imgSan);

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
                Intent intent = new Intent(context, UserDatSan.class);
                intent.putExtra("tenSan", sanList.get(holder.getAdapterPosition()).getTenSan());
                intent.putExtra("imageSan", sanList.get(holder.getAdapterPosition()).getImageSan());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sanList.size();
    }

    public void SearchData(ArrayList<San> list) {
        sanList = list;
        notifyDataSetChanged();
    }
}
