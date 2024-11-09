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

import com.example.ltdd2_datsancaulong.Models.San;
import com.example.ltdd2_datsancaulong.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class ThemSan extends AppCompatActivity {
    ImageView imgSan, imgUpload;
    TextView txtIDSan;
    EditText edtTenSan;
    Button btnThem;
    int PICK_IMAGE_REQUEST = 1;
    Uri imageUri;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san);
        setControl();
        setEvent();
    }

    private void setEvent() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thêm sân");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("San");
        firebaseStorage = FirebaseStorage.getInstance();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                , new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if (o.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = o.getData();
                            imageUri = intent.getData();
                            imgSan.setImageURI(imageUri);
                        } else {
                            Toast.makeText(ThemSan.this, "No Image Selected!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertSan();
                onBackPressed();
            }
        });

    }

    private void InsertSan() {
//        final StorageReference storageReference = firebaseStorage.getReference().child("San").child(System.currentTimeMillis() + "");
//        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
        San san = new San();
        san.setIdSan(databaseReference.push().getKey());
        san.setTenSan(edtTenSan.getText().toString());

        if (imageUri != null) {
            String name = String.valueOf(System.currentTimeMillis());
            try {
                File file = new File(getPath(imageUri));
                File temp = getCacheDir();
                File te = File.createTempFile("s_" + name, ".png", temp);

                copy(file, te);
                san.setImageSan(te.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            san.setImageSan("");
        }

        databaseReference.child(san.getIdSan()).child(txtIDSan.getText().toString()).setValue(san);
        DanhSachSan.sanList.clear();
        DanhSachSan.sanAdapter.notifyDataSetChanged();

//                    }
//                });
//            }
//        });
    }

    public static void copy(File src, File dst) throws IOException {
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                imgSan.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
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
        imgSan = findViewById(R.id.imgSan);
        imgUpload = findViewById(R.id.imgUpload);
        txtIDSan = findViewById(R.id.txtIDSan);
        edtTenSan = findViewById(R.id.edtTenSan);
        btnThem = findViewById(R.id.btnThemSan);
    }
}