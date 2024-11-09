package com.example.ltdd2_datsancaulong.Activity.User;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.ltdd2_datsancaulong.Adapter.SanDaThueAdapter;
import com.example.ltdd2_datsancaulong.ClickListtener.DanhSachSanDaThueClickListener;
import com.example.ltdd2_datsancaulong.Models.DatSan;
import com.example.ltdd2_datsancaulong.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DanhSachSanDaDatUser extends AppCompatActivity implements DanhSachSanDaThueClickListener {
    RecyclerView recyclerView;
    static SanDaThueAdapter sanDaThueAdapter;
     static List<DatSan> datSanList = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_san_da_dat);
        setControl();
        setEvent();

    }

    private void setEvent() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Danh sách sân đã cho thuê");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("DatSan");


        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        sanDaThueAdapter = new SanDaThueAdapter(this, datSanList, this);
        recyclerView.setAdapter(sanDaThueAdapter);
        GetListDanhSachSanDaChoThue();

    }

    public void GetListDanhSachSanDaChoThue() {
        datSanList.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DatSan datSan = dataSnapshot.getValue(DatSan.class);
                    datSanList.add(datSan);

                }
                sanDaThueAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onItemcClick(DatSan datSan) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_chitiet_nguoithue_user);
        TextView txtChiTietTenKhachHang, txtChiTietSoDienThoai, txtChiTietNgayThue, txtChiTietTenSan, txtChiTietKhungGio, txtChiTietGiaThue;
        txtChiTietTenKhachHang = dialog.findViewById(R.id.txtChiTietTenKhachHang);
        txtChiTietSoDienThoai = dialog.findViewById(R.id.txtChiTietSoDienThoai);
        txtChiTietNgayThue = dialog.findViewById(R.id.txtChiTietNgayThue);
        txtChiTietTenSan = dialog.findViewById(R.id.txtChiTietTenSan);
        txtChiTietKhungGio = dialog.findViewById(R.id.txtChiTietKhungGio);
        txtChiTietGiaThue = dialog.findViewById(R.id.txtChiTietGiaThue);

        txtChiTietTenKhachHang.setText(datSan.getTenKhachHang());
        txtChiTietSoDienThoai.setText(datSan.getSoDienThoai());
        txtChiTietNgayThue.setText(datSan.getNgayThue());
        txtChiTietTenSan.setText(datSan.getTenSan());
        txtChiTietKhungGio.setText(datSan.getKhungGio());
        txtChiTietGiaThue.setText(datSan.getGia());

        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
        recyclerView = findViewById(R.id.recyclerView);
    }


}