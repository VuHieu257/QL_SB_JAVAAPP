package com.example.ltdd2_datsancaulong.Activity.Admin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ltdd2_datsancaulong.Models.KhachHang;
import com.example.ltdd2_datsancaulong.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChiTietKhachHang extends AppCompatActivity {
    TextView txtIDKhachHang;
    EditText edtTenKhachHang, edtSoDienThoai, edtPassword, edtConfirm;
    ImageView imgKhachHang;
    Button btnSua, btnXoa;
    FloatingActionButton floatingActionButton;
    KhachHang khachHang;
    Uri uri;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    FirebaseDatabase firebaseDatabase;
    String oldImageUrl = "";
    int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_khach_hang);
        setControl();
        setEvent();
    }

    private void setEvent() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi tiết khách hàng");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        firebaseStorage = FirebaseStorage.getInstance();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult o) {
                            if (o.getResultCode() == Activity.RESULT_OK) {
                                Intent data = o.getData();
                                uri = data.getData();
                                imgKhachHang.setImageURI(uri);

                            } else {
                                Toast.makeText(ChiTietKhachHang.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
            Picasso.get().load(bundle.getString("imageKhachHang")).into(imgKhachHang);
            txtIDKhachHang.setText(bundle.getString("idKhachHang"));
            edtTenKhachHang.setText(bundle.getString("tenKhachHang"));
            edtSoDienThoai.setText(bundle.getString("soDienThoai"));
            edtPassword.setText(bundle.getString("password"));
            edtConfirm.setText(bundle.getString("conFirm"));
            oldImageUrl = bundle.getString("imageKhachHang");
        }
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase.getReference().child("KhachHang").child(txtIDKhachHang.getText().toString()).removeValue();
                DanhSachKhachHang.khachHangList.clear();
                onBackPressed();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenLibrary();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuaKhachhang();
            }
        });
    }

    private void OpenLibrary() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            try {
                imgKhachHang.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), uri));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void SuaKhachhang() {
        StorageReference storageReference = firebaseStorage.getReference().child("KhachHang");
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Map<String, Object> suaKhachHang = new HashMap<String, Object>();
                        suaKhachHang.put("tenKhachHang", edtTenKhachHang.getText().toString());
                        suaKhachHang.put("soDienThoai", edtSoDienThoai.getText().toString());
                        suaKhachHang.put("password", edtPassword.getText().toString());
                        suaKhachHang.put("conFirm", edtConfirm.getText().toString());
                        suaKhachHang.put("imageKhachHang", uri.toString());
                        firebaseDatabase.getReference().child("KhachHang").child(txtIDKhachHang.getText().toString()).updateChildren(suaKhachHang);
                        DanhSachKhachHang.khachHangList.clear();
                        onBackPressed();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });
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
        imgKhachHang = findViewById(R.id.imageViewNguoiThue);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        txtIDKhachHang = findViewById(R.id.txtIDNguoiThue);
        edtTenKhachHang = findViewById(R.id.edtHoTen);
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirm = findViewById(R.id.edtConfirm);
        btnSua = findViewById(R.id.btnSuaNguoiThue);
        btnXoa = findViewById(R.id.btnXoaNguoiThue);
    }
}