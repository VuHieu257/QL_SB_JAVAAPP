package com.example.ltdd2_datsancaulong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    EditText edtHoTen, edtSdt, edtConfirm;
    Button btnDangKi;
    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangKi();
            }
        });
    }

    private void dangKi() {
        String email, matKhau,sdt,name;
        email = edtEmail.getText().toString().trim();
        matKhau = edtPassword.getText().toString().trim();
        name = edtHoTen.getText().toString().trim();
        sdt = edtSdt.getText().toString().trim();
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, matKhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                    Map<String, Object> user = new HashMap<>();
                    user.put("email", email);
                    user.put("password", matKhau);
                    user.put("name", name);
                    user.put("phoneNumber", sdt);
                    user.put("createdAt", FieldValue.serverTimestamp());

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("users").document(userId)
                            .set(user)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(getApplicationContext(), "Tạo tài khoản thành công và lưu vào Firestore", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            })
                            .addOnFailureListener(e -> {
                                Log.e("Firestore Error", e.getMessage());
                                Toast.makeText(getApplicationContext(), "Lưu thông tin người dùng không thành công", Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "Tạo tài khoản không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //    private void dangKi() {
//        String email, hoTen, sdt, matKhau, matKhauConfirm;
//        email = edtEmail.getText().toString().trim();
//        hoTen = edtHoTen.getText().toString().trim();
//        sdt = edtSdt.getText().toString().trim();
//        matKhau = edtPassword.getText().toString().trim();
//        matKhauConfirm = edtConfirm.getText().toString().trim();
//
//        if (!matKhau.equals(matKhauConfirm)) {
//            Toast.makeText(getApplicationContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        progressDialog.show();
//
//        mAuth.createUserWithEmailAndPassword(email,matKhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                progressDialog.dismiss();
//                if (task.isSuccessful()) {
//                    String userId = mAuth.getCurrentUser ().getUid();
//
//                    Map<String, Object> user = new HashMap<>();
////                    user.put("hoTen", hoTen);
////                    user.put("soDienThoai", sdt);
//                    user.put("email", email);
//                    user.put("password", matKhau);
//                    user.put("createdAt", FieldValue.serverTimestamp());
//
//                    // Lưu thông tin người dùng vào Firestore
//                    FirebaseFirestore db = FirebaseFirestore.getInstance();
//                    db.collection("users").document(userId)
//                            .set(user)
//                            .addOnSuccessListener(aVoid -> {
//                                Toast.makeText(getApplicationContext(), "Tạo tài khoản thành công và lưu vào Firestore", Toast.LENGTH_SHORT).show();
//                                onBackPressed();
//                            })
//                            .addOnFailureListener(e -> {
//                                Toast.makeText(getApplicationContext(), "Lưu thông tin người dùng không thành công", Toast.LENGTH_SHORT).show();
//                            });
//                } else {
//                    Toast.makeText(getApplicationContext(), "Tạo tài khoản không thành công", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
    private void setControl() {
        edtEmail = findViewById(R.id.edtEmail);
        edtHoTen = findViewById(R.id.edtHoten);
        edtSdt = findViewById(R.id.edtSoDienThoai);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirm = findViewById(R.id.edtConfirm);

        btnDangKi = findViewById(R.id.btnDangKy);
        progressDialog = new ProgressDialog(this);
        if (edtEmail == null || edtHoTen == null || edtSdt == null || edtPassword == null || edtConfirm == null) {
            Log.e("Register", "Some EditText views are null");
        }
    }
}