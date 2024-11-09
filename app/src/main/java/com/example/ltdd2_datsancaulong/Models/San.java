package com.example.ltdd2_datsancaulong.Models;

public class San {
    String idSan, tenSan, imageSan;

    public San(String idSan, String tenSan, String imageSan) {
        this.idSan = idSan;
        this.tenSan = tenSan;
        this.imageSan = imageSan;
    }

    public San() {
    }

    public String getIdSan() {
        return idSan;
    }

    public void setIdSan(String idSan) {
        this.idSan = idSan;
    }

    public String getTenSan() {
        return tenSan;
    }

    public void setTenSan(String tenSan) {
        this.tenSan = tenSan;
    }

    public String getImageSan() {
        return imageSan;
    }

    public void setImageSan(String imageSan) {
        this.imageSan = imageSan;
    }

    @Override
    public String toString() {
        return "San{" +
                "idSan='" + idSan + '\'' +
                ", tenSan='" + tenSan + '\'' +
                ", imageSan='" + imageSan + '\'' +
                '}';
    }
}
