package com.example.ltdd2_datsancaulong.Activity.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ltdd2_datsancaulong.Adapter.SanAdapter;
import com.example.ltdd2_datsancaulong.ClickListtener.SanClickListener;
import com.example.ltdd2_datsancaulong.Models.DatSan;
import com.example.ltdd2_datsancaulong.Models.San;
import com.example.ltdd2_datsancaulong.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DanhSachSan extends AppCompatActivity implements SanClickListener {
    SearchView searchView;
    RecyclerView recyclerViewSan;
    static List<San> sanList = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    static SanAdapter sanAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_san);
        setControl();
        setEvent();
    }

    private void setEvent() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Danh sách sân");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("San");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewSan.setLayoutManager(linearLayoutManager);
        sanAdapter = new SanAdapter(this, sanList, this);
        recyclerViewSan.setAdapter(sanAdapter);
        GetListSan();
    }

    private void GetListSan() {
        sanList.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    San san = dataSnapshot.getValue(San.class);
                    sanList.add(san);
                }
                sanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(San san) {
        Intent intent = new Intent(DanhSachSan.this, AdminDatSan.class);
        startActivity(intent);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_san, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.mnSan:
                Intent intent = new Intent(DanhSachSan.this, ThemSan.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
        searchView = findViewById(R.id.searchSan);
        recyclerViewSan = findViewById(R.id.recyclerViewSan);
    }



}