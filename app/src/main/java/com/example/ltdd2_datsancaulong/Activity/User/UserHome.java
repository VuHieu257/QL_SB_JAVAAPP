package com.example.ltdd2_datsancaulong.Activity.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ltdd2_datsancaulong.Adapter.SanUserAdpter;
import com.example.ltdd2_datsancaulong.ClickListtener.SanClickListener;
import com.example.ltdd2_datsancaulong.Models.San;
import com.example.ltdd2_datsancaulong.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserHome extends AppCompatActivity implements SanClickListener {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    SearchView searchView;
    RecyclerView recyclerView;
    SanUserAdpter sanUserAdpter;
    List<San> sanList = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        setControl();
        setEvent();
    }

    private void setEvent() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Home");


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("San");

        ActionDrawerToggle();
        RecyclerViewSanUser();
        GetDataFirebae();
        SearchData();
    }

    private void SearchData() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchSan(newText);
                return true;
            }
        });
    }

    private void SearchSan(String newText) {
        ArrayList<San> sanArrayList = new ArrayList<>();
        for (San san : sanList){
            if (san.getTenSan().toLowerCase().contains(newText.toLowerCase())){
                sanArrayList.add(san);
            }
        }
        sanUserAdpter.SearchData(sanArrayList);
    }

    private void GetDataFirebae() {
        sanList.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    San san = dataSnapshot.getValue(San.class);
                    sanList.add(san);
                }
                sanUserAdpter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void RecyclerViewSanUser() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sanUserAdpter = new SanUserAdpter(this, sanList, this);
        recyclerView.setAdapter(sanUserAdpter);
    }

    private void ActionDrawerToggle() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mnDanhSachSanThue:
                        break;
                    case R.id.mnHoaDon:
                        Intent intent = new Intent(UserHome.this, DanhSachSanDaDatUser.class);
                        startActivity(intent);
                        break;
                    case R.id.mnInfo:
                        finish();
                        break;
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerlayout);
        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    public void onItemClick(San san) {
        Intent intent = new Intent(UserHome.this, UserDatSan.class);
        startActivity(intent);
    }
}