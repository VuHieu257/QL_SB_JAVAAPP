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

import com.example.ltdd2_datsancaulong.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChiTietSan extends AppCompatActivity {
    TextView txtIDSan;
    EditText edtTenSan;
    ImageView imgSan, imgUpload;
    Button btnSua;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    Uri uri;
    String imageUri = "";
    int PICK_IMAGE_REQUEST =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san);
        setControl();
        setEvent();
    }

    private void setEvent() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sửa sân");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("San");
        firebaseStorage = FirebaseStorage.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult o) {
                            if (o.getResultCode() == Activity.RESULT_OK) {
                                Intent data = new Intent();
                                uri = data.getData();
                                imgSan.setImageURI(uri);
                            }
                            else{
                                Toast.makeText(ChiTietSan.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            Picasso.get().load(bundle.getString("imageSan")).into(imgSan);
            txtIDSan.setText(bundle.getString("idSan"));
            edtTenSan.setText(bundle.getString("tenSan"));
            imageUri = bundle.getString("imageSan");
        }
        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenLibrary();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuaSan();
            }
        });

    }

    private void SuaSan() {
        StorageReference storageReference = firebaseStorage.getReference().child(System.currentTimeMillis() + "");
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                   @Override
                   public void onSuccess(Uri uri) {
                       Map<String, Object> suaSan = new HashMap<>();
                       suaSan.put("tenSan", edtTenSan.getText().toString());
                       suaSan.put("imageSan", uri.toString());
                       databaseReference.child(txtIDSan.getText().toString()).updateChildren(suaSan);
                       DanhSachSan.sanList.clear();
                       DanhSachSan.sanAdapter.notifyDataSetChanged();

                   }
               });
            }
        });
        onBackPressed();
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
                imgSan.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), uri));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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
        btnSua = findViewById(R.id.btnSua);
    }
}