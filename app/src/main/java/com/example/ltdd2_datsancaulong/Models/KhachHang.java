package com.example.ltdd2_datsancaulong.Models;

import java.io.Serializable;

//public class KhachHang implements Serializable {
//    String idKhachHang, tenKhachHang, soDienThoai, password, conFirm;
//    String imageKhachHang;
//
//    public KhachHang(String idKhachHang, String tenKhachHang, String soDienThoai, String password, String conFirm, String imageKhachHang) {
//        this.idKhachHang = idKhachHang;
//        this.tenKhachHang = tenKhachHang;
//        this.soDienThoai = soDienThoai;
//        this.password = password;
//        this.conFirm = conFirm;
//        this.imageKhachHang = imageKhachHang;
//    }
//
//    public KhachHang() {
//    }
//
//    public String getIdKhachHang() {
//        return idKhachHang;
//    }
//
//    public void setIdKhachHang(String idKhachHang) {
//        this.idKhachHang = idKhachHang;
//    }
//
//    public String getTenKhachHang() {
//        return tenKhachHang;
//    }
//
//    public void setTenKhachHang(String tenKhachHang) {
//        this.tenKhachHang = tenKhachHang;
//    }
//
//    public String getSoDienThoai() {
//        return soDienThoai;
//    }
//
//    public void setSoDienThoai(String soDienThoai) {
//        this.soDienThoai = soDienThoai;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getConFirm() {
//        return conFirm;
//    }
//
//    public void setConFirm(String conFirm) {
//        this.conFirm = conFirm;
//    }
//
//    public String getImageKhachHang() {
//        return imageKhachHang;
//    }
//
//    public void setImageKhachHang(String imageKhachHang) {
//        this.imageKhachHang = imageKhachHang;
//    }
//
//    @Override
//    public String toString() {
//        return "KhachHang{" +
//                "idKhachHang='" + idKhachHang + '\'' +
//                ", tenKhachHang='" + tenKhachHang + '\'' +
//                ", soDienThoai='" + soDienThoai + '\'' +
//                ", password='" + password + '\'' +
//                ", conFirm='" + conFirm + '\'' +
//                ", imageKhachHang=" + imageKhachHang +
//                '}';
//    }
//}

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;
import java.io.Serializable;

public class KhachHang implements Serializable {
    private String idKhachHang; // ID của khách hàng
    private String tenKhachHang; // Tên khách hàng
    private String soDienThoai; // Số điện thoại
    private String password; // Mật khẩu
    private String conFirm; // Xác nhận mật khẩu
    private String email; // Email
    private Timestamp createdAt; // Thời gian tạo
    private String imageKhachHang; // Hình ảnh khách hàng

    // Constructor với tất cả các trường
    public KhachHang(String idKhachHang, String tenKhachHang, String soDienThoai, String password, String conFirm, String email, Timestamp  createdAt, String imageKhachHang) {
        this.idKhachHang = idKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.password = password;
        this.conFirm = conFirm;
        this.email = email;
        this.createdAt = createdAt;
        this.imageKhachHang = imageKhachHang;
    }

    // Constructor mặc định
    public KhachHang() {
    }

    @PropertyName("idKhachHang")
    public String getIdKhachHang() {
        return idKhachHang;
    }

    @PropertyName("idKhachHang")
    public void setIdKhachHang(String idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    @PropertyName("name")
    public String getTenKhachHang() {
        return tenKhachHang;
    }

    @PropertyName("name")
    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    @PropertyName("phoneNumber")
    public String getSoDienThoai() {
        return soDienThoai;
    }

    @PropertyName("phoneNumber")
    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    @PropertyName("password")
    public String getPassword() {
        return password;
    }

    @PropertyName("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @PropertyName("conFirm")
    public String getConFirm() {
        return conFirm;
    }

    @PropertyName("conFirm")
    public void setConFirm(String conFirm) {
        this.conFirm = conFirm;
    }

    @PropertyName("email")
    public String getEmail() {
        return email;
    }

    @PropertyName("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @PropertyName("createdAt")
    public Timestamp  getCreatedAt() {
        return createdAt;
    }

    @PropertyName("createdAt")
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @PropertyName("imageKhachHang")
    public String getImageKhachHang() {
        return imageKhachHang;
    }

    @PropertyName("imageKhachHang")
    public void setImageKhachHang(String imageKhachHang) {
        this.imageKhachHang = imageKhachHang;
    }

    @Override
    public String toString() {
        return "KhachHang{" +
                "idKhachHang='" + idKhachHang + '\'' +
                ", tenKhachHang='" + tenKhachHang + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", password='" + password + '\'' +
                ", conFirm='" + conFirm + '\'' +
                ", email='" + email + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", imageKhachHang='" + imageKhachHang + '\'' +
                '}';
    }
}