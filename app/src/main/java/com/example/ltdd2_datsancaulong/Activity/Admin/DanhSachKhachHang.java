package com.example.ltdd2_datsancaulong.Activity.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ltdd2_datsancaulong.Adapter.KhachHangAdapter;
import com.example.ltdd2_datsancaulong.ClickListtener.KhachHangClickListenner;
import com.example.ltdd2_datsancaulong.Models.KhachHang;
import com.example.ltdd2_datsancaulong.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DanhSachKhachHang extends AppCompatActivity implements KhachHangClickListenner {
    SearchView searchView;
    RecyclerView recyclerViewKhachHang;
    static List<KhachHang> khachHangList = new ArrayList<>();
    static KhachHangAdapter khachHangAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_danh_sach_khach_hang);
        setControl();
        setEvent();
    }

    private void setEvent() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Danh sách khách hàng");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("KhachHang");

        RecyclerViewKhachHang();
        ListKhachHang();
    }

    private void ListKhachHang() {
        khachHangList.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    KhachHang khachHang = dataSnapshot.getValue(KhachHang.class);
                    khachHangList.add(khachHang);
                }
                khachHangAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void RecyclerViewKhachHang() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewKhachHang.setLayoutManager(linearLayoutManager);
        khachHangAdapter = new KhachHangAdapter(this, khachHangList, this);
        recyclerViewKhachHang.setAdapter(khachHangAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_khach_hang, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.mnAddKhachHang:
                Intent intent = new Intent(DanhSachKhachHang.this, ThemKhachHang.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
        searchView = findViewById(R.id.searchKhachHang);
        recyclerViewKhachHang = findViewById(R.id.recyclerViewKhachHang);
    }

    @Override
    public void OnItemClick(KhachHang khachHang) {
        Intent intent = new Intent(DanhSachKhachHang.this, ChiTietKhachHang.class);
        startActivity(intent);
    }

    @Override
    public void OnItemLongClick(KhachHang khachHang) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Xóa người thuê");
//        builder.setMessage("Bạn có muốn xóa người thuê này không!!");
//        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                firebaseDatabase.getReference("KhachHang").child(ThemKhachHang.txtIDKhachHang.getText().toString()).removeValue();
//                khachHangList.clear();
//                khachHangAdapter.notifyDataSetChanged();
//                dialogInterface.dismiss();
//            }
//        });
//        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//        builder.show();
    }


}
//public class DanhSachKhachHang extends AppCompatActivity implements KhachHangClickListenner {
//    SearchView searchView;
//    RecyclerView recyclerViewKhachHang;
//    static List<KhachHang> khachHangList = new ArrayList<>();
//    static KhachHangAdapter khachHangAdapter;
//    FirebaseFirestore firebaseFirestore; // Sử dụng Firestore
//    // FirebaseDatabase firebaseDatabase; // Nếu sử dụng Realtime Database
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_admin_danh_sach_khach_hang);
//        setControl();
//        setEvent();
//    }
//
//    private void setEvent() {
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Danh sách khách hàng");
//
//        firebaseFirestore = FirebaseFirestore.getInstance();
//        // firebaseDatabase = FirebaseDatabase.getInstance(); z
//
//        RecyclerViewKhachHang();
//        ListKhachHang();
//    }
//
//    private void ListKhachHang() {
//        khachHangList.clear();
//        // Lấy dữ liệu từ Firestore
//        firebaseFirestore.collection("users")
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            KhachHang khachHang = document.toObject(KhachHang.class);
//                            khachHangList.add(new KhachHang());
//                        }
//                        khachHangAdapter.notifyDataSetChanged();
//                    } else {
//                        // Xử lý lỗi nếu có
//                        Toast.makeText(DanhSachKhachHang.this, "Lỗi: " + task.getException(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        // Nếu sử dụng Realtime Database
//        /*
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    KhachHang khachHang = dataSnapshot.getValue(KhachHang.class);
//                    khachHangList.add(khachHang);
//                }
//                khachHangAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Xử lý lỗi nếu có
//            }
//        });
//        */
//    }
//
//    private void RecyclerViewKhachHang() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerViewKhachHang.setLayoutManager(linearLayoutManager);
//        khachHangAdapter = new KhachHangAdapter(this, khachHangList, this);
//        recyclerViewKhachHang.setAdapter(khachHangAdapter);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_khach_hang, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                break;
//            case R.id.mnAddKhachHang:
//                Intent intent = new Intent(DanhSachKhachHang.this, ThemKhachHang.class);
//                startActivity(intent);
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void setControl() {
//        searchView = findViewById(R.id.searchKhachHang);
//        recyclerViewKhachHang = findViewById(R.id.recyclerViewKhachHang);
//    }
//
//    @Override
//    public void OnItemClick(KhachHang khachHang) {
//        Intent intent = new Intent(DanhSachKhachHang.this, ChiTietKhachHang.class);
//        intent.putExtra("idKhachHang", khachHang.getIdKhachHang());
//        intent.putExtra("tenKhachHang", khachHang.getTenKhachHang());
//        intent.putExtra("soDienThoai", khachHang.getSoDienThoai());
//        intent.putExtra("password", khachHang.getPassword());
//        intent.putExtra("conFirm", khachHang.getConFirm());
//        intent.putExtra("imageKhachHang", khachHang.getImageKhachHang());
//        startActivity(intent);
//    }
//
//    @Override
//    public void OnItemLongClick(KhachHang khachHang) {
//        // Xử lý sự kiện click dài ở đây nếu cần
//    }
//}