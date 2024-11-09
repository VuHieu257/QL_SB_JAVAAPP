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
import android.database.Cursor;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Objects;

public class ThemKhachHang extends AppCompatActivity {
    TextView txtIDKhachHang;
    EditText edtTenKhachHang, edtSoDienThoai, edtPassword, edtConfirm;
    ImageView imgKhachHang;
    Button btnThem;
    FloatingActionButton floatingActionButton;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseStorage firebaseStorage;

    Uri imageUri;
    int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_khach_hang);
        setControl();
        setEvent();
    }

    private void setEvent() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thêm khách hàng");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("KhachHang");
        firebaseStorage = FirebaseStorage.getInstance();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if (o.getResultCode() == Activity.RESULT_OK) {
                            Intent data = o.getData();
                            imageUri = data.getData();
                            imgKhachHang.setImageURI(imageUri);
                        } else {
                            Toast.makeText(ThemKhachHang.this, "No Image Selected!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertKhachHang();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void InsertKhachHang() {
        KhachHang khachHang = new KhachHang();
        khachHang.setIdKhachHang(databaseReference.push().getKey());
        khachHang.setTenKhachHang(edtTenKhachHang.getText().toString());
        khachHang.setSoDienThoai(edtSoDienThoai.getText().toString());
        khachHang.setPassword(edtPassword.getText().toString());
        khachHang.setConFirm(edtConfirm.getText().toString());
        if (imageUri != null) {
            String name = String.valueOf(System.currentTimeMillis());
            try {
                File file = new File(getPath(imageUri));
                File temp = getCacheDir();
                File te = File.createTempFile("kh_" + name, ".png", temp);

                copy(file, te);
                khachHang.setImageKhachHang(te.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            khachHang.setImageKhachHang("");
        }
        databaseReference.child(khachHang.getIdKhachHang()).child(txtIDKhachHang.getText().toString()).setValue(khachHang);
        DanhSachKhachHang.khachHangList.clear();

        onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                imgKhachHang.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void copy(File src, File dst) throws IOException {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            try (InputStream in = Files.newInputStream(src.toPath())) {
                try (OutputStream out = Files.newOutputStream(dst.toPath())) {
                    // Transfer bytes from in to out
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                }
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
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
        btnThem = findViewById(R.id.btnThemNguoiThue);
    }
}