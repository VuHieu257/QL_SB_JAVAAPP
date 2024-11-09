package com.example.ltdd2_datsancaulong.Models;

public class DatSan {
    String idDatSan, tenSan, tenKhachHang, soDienThoai,ngayThue, khungGio, gia;

    public DatSan(String idDatSan, String tenSan, String tenKhachHang, String soDienThoai, String ngayThue, String khungGio, String gia) {
        this.idDatSan = idDatSan;
        this.tenSan = tenSan;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.ngayThue = ngayThue;
        this.khungGio = khungGio;
        this.gia = gia;
    }

    public DatSan() {
    }

    public String getIdDatSan() {
        return idDatSan;
    }

    public void setIdDatSan(String idDatSan) {
        this.idDatSan = idDatSan;
    }

    public String getTenSan() {
        return tenSan;
    }

    public void setTenSan(String tenSan) {
        this.tenSan = tenSan;
    }


    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getNgayThue() {
        return ngayThue;
    }

    public void setNgayThue(String ngayThue) {
        this.ngayThue = ngayThue;
    }

    public String getKhungGio() {
        return khungGio;
    }

    public void setKhungGio(String khungGio) {
        this.khungGio = khungGio;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }


    @Override
    public String toString() {
        return "DatSan{" +
                "idDatSan='" + idDatSan + '\'' +
                ", tenSan='" + tenSan + '\'' +
                ", tenKhachHang='" + tenKhachHang + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", ngayThue='" + ngayThue + '\'' +
                ", khungGio='" + khungGio + '\'' +
                ", gia='" + gia + '\'' +
                '}';
    }
}
