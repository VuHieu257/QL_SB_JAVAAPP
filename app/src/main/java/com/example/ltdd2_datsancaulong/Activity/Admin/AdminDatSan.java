package com.example.ltdd2_datsancaulong.Activity.Admin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ltdd2_datsancaulong.Models.DatSan;
import com.example.ltdd2_datsancaulong.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class AdminDatSan extends AppCompatActivity {
    TextView txtIDDatSan, txtTenSan, txtNgayThue, txtGiaSan;
    EditText edtHoTenKhachHang, edtSoDT;
    ImageView imgSan;
    Spinner spnKhungGio;
    Button btnDatSan;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Uri uri;
    ArrayAdapter arrayAdapter;
    List<String> stringList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dat_san);
        setControl();
        setEvent();
    }

    private void setEvent() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đặt sân");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("DatSan");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult o) {
                            if (o.getResultCode() == Activity.RESULT_OK) {
                                Intent intent = new Intent();
                                uri = intent.getData();
                                imgSan.setImageURI(uri);
                            }
                        }
                    });
            Picasso.get().load(bundle.getString("imageSan")).into(imgSan);
            txtTenSan.setText(bundle.getString("tenSan"));
        }
        KhoiTao();
        FuncDatePicker();
        spnKhungGio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spnKhungGio.getSelectedItem().equals("7:00 - 8:00")) {
                    txtGiaSan.setText("190000");
                }
                if (spnKhungGio.getSelectedItem().equals("8:00 - 9:00")) {
                    txtGiaSan.setText("200000");
                }
                if (spnKhungGio.getSelectedItem().equals("9:00 - 10:00")) {
                    txtGiaSan.setText("180000");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnDatSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminDatSanKH();
            }
        });

    }

    private void FuncDatePicker() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        txtNgayThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminDatSan.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        if (i2 >= day && i1 >= month && i >= year) {
                            txtNgayThue.setText(i2 + "/" + i1 + "/" + i);
                        } else {
                            txtNgayThue.setText("Vui lòng chọn lại ngày");
                        }
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void KhoiTao() {
        stringList.add("7:00 - 8:00");
        stringList.add("8:00 - 9:00");
        stringList.add("9:00 - 10:00");
        stringList.add("16:00 - 17:00");
        stringList.add("17:00 - 18:00");
        stringList.add("18:00 - 19:00");
        stringList.add("19:00 - 20:00");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stringList);
        spnKhungGio.setAdapter(arrayAdapter);


    }

    String strIDDatSan, strTen, strDT, strTenSan, strNgayThue, strKhungGio, strGia;

    public void AdminDatSanKH() {

        DatSan datSan = new DatSan();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datSan.setIdDatSan(databaseReference.push().getKey());
                datSan.setTenKhachHang(edtHoTenKhachHang.getText().toString());
                datSan.setSoDienThoai(edtSoDT.getText().toString());
                datSan.setTenSan(txtTenSan.getText().toString());
                datSan.setNgayThue(txtNgayThue.getText().toString());
                datSan.setKhungGio(spnKhungGio.getSelectedItem().toString());
                datSan.setGia(txtGiaSan.getText().toString());

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DatSan item = dataSnapshot.getValue(DatSan.class);
                    if (Objects.equals(item.getTenSan(), datSan.getTenSan())
                            && Objects.equals(item.getNgayThue(), datSan.getNgayThue())
                            && Objects.equals(item.getKhungGio(), datSan.getKhungGio())) {
                        Toast.makeText(getApplicationContext(), "Sân đã được đặt\nVui long chon gio khac", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Toast.makeText(AdminDatSan.this, "Đặt sân thành công", Toast.LENGTH_SHORT).show();

                    }
                }
                databaseReference.child(datSan.getIdDatSan()).child(txtIDDatSan.getText().toString()).setValue(datSan);
                DanhSachSanDaDat.datSanList.clear();
                onBackPressed();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        edtHoTenKhachHang = findViewById(R.id.edtTenKhachHang);
        edtSoDT = findViewById(R.id.edtSDTKhachHang);
        txtTenSan = findViewById(R.id.txtTenSan);
        txtIDDatSan = findViewById(R.id.txtIDDatSan);
        txtNgayThue = findViewById(R.id.txtNgayThue);
        txtGiaSan = findViewById(R.id.txtGiaSan);
        imgSan = findViewById(R.id.imgSan);
        spnKhungGio = findViewById(R.id.spnKhungGio);
        btnDatSan = findViewById(R.id.btnDatSan);
    }
}