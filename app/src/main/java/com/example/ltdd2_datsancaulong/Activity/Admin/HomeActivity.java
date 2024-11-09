package com.example.ltdd2_datsancaulong.Activity.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.ltdd2_datsancaulong.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ViewFlipper viewFlipper, viewFlipperSan;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        setControl();
        setEvent();
    }

    private void setEvent() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Home");
        ActionViewFlipper();
        ActionDrawerToggle();
        ActionViewFlipperSan();

    }

    private void ActionDrawerToggle() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mnKhachHang:
                        Intent intent = new Intent(HomeActivity.this, DanhSachKhachHang.class);
                        startActivity(intent);
                        break;
                    case R.id.mnSan:
                        Intent intentSan = new Intent(HomeActivity.this, DanhSachSan.class);
                        startActivity(intentSan);
                        break;
                    case R.id.mnDanhSachSanDaDat:
                        Intent intentDanhSachSanDaDat = new Intent(HomeActivity.this, DanhSachSanDaDat.class);
                        startActivity(intentDanhSachSanDaDat);
                        break;
                    case R.id.mnLogout:
                        finish();
                        break;
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }

    private void ActionViewFlipper() {
        ArrayList<Integer> mangQuangCao = new ArrayList<>();
        mangQuangCao.add(R.drawable.imgbanner1);
        mangQuangCao.add(R.drawable.imgbanner2);
        mangQuangCao.add(R.drawable.imgbanner3);
        mangQuangCao.add(R.drawable.imgbanner4);

        for (int i = 0; i < mangQuangCao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(mangQuangCao.get(i)).into(imageView);

            //chỉnh tấm hình vừa khung quảng cáo
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //thêm ảnh imgview vào Viewflipper
            viewFlipper.addView(imageView);
        }
        //thiết lạp chạy tự động cho viewFlipper
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        //gọi animation cho vào ra
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);

        //gọi animation vào viewflipper
        viewFlipper.setAnimation(animation_slide_in);
        viewFlipper.setAnimation(animation_slide_out);
    }
    private void ActionViewFlipperSan() {
        ArrayList<Integer> mangQuangCao = new ArrayList<>();
        mangQuangCao.add(R.drawable.imgsan1);
        mangQuangCao.add(R.drawable.imgsan2);
        mangQuangCao.add(R.drawable.imgsan3);

        for (int i = 0; i < mangQuangCao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(mangQuangCao.get(i)).into(imageView);

            //chỉnh tấm hình vừa khung quảng cáo
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //thêm ảnh imgview vào Viewflipper
            viewFlipperSan.addView(imageView);
        }
        //thiết lạp chạy tự động cho viewFlipper
        viewFlipperSan.setFlipInterval(4000);
        viewFlipperSan.setAutoStart(true);

        //gọi animation cho vào ra
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);

        //gọi animation vào viewflipper
        viewFlipperSan.setAnimation(animation_slide_in);
        viewFlipperSan.setAnimation(animation_slide_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
        viewFlipper = findViewById(R.id.viewflipperHome);
        viewFlipperSan = findViewById(R.id.viewflipperSan);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationView);
    }
}