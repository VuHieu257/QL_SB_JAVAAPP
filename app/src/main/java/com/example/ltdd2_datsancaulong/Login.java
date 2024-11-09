package com.example.ltdd2_datsancaulong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ltdd2_datsancaulong.Activity.Admin.HomeActivity;
import com.example.ltdd2_datsancaulong.Activity.User.UserHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity {
    EditText edtTaiKhoan, edtMatKhau;
    Button btnDangNhap, btnAdmin;
    TextView txtRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        setControl();
        setEvent();
    }

    private void setEvent() {
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangNhap();
            }
        });
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Login.this, HomeActivity.class);
                startActivity(intent1);
            }
        });
    }

    private void dangNhap() {
        String taiKhoan, matKhau;
        taiKhoan = edtTaiKhoan.getText().toString();
        matKhau = edtMatKhau.getText().toString();


        edtTaiKhoan.requestFocus();
        //emty field
        if (TextUtils.isEmpty(taiKhoan)) {
            edtTaiKhoan.setError("Vui lòng nhập tài khoản");
            return;
        }
        if (TextUtils.isEmpty(matKhau)) {
            edtMatKhau.setError("Vui lòng nhập mật khẩu");
            return;
        }

        mAuth.signInWithEmailAndPassword(taiKhoan, matKhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                    if (isAdmin(taiKhoan, matKhau)) {
                        Intent intent = new Intent(Login.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(Login.this, UserHome.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isAdmin(String taiKhoan, String password) {
        if (taiKhoan == "tien123@gmail.com" && password == "1234567") {
            Toast.makeText(this, "Admin", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }
    }


    private void setControl() {
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtMatKhau = findViewById(R.id.edtPassword);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnAdmin = findViewById(R.id.btnAdmin);
        txtRegister = findViewById(R.id.txtRegister);
    }
}