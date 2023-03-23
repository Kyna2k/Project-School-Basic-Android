package com.example.assignment.model;

public class SinhVien {
    private String msv;
    private String tensv;
    private String ngaysinh;
    private String hinhsv;
    private String lop;

    public SinhVien(String msv, String tensv, String ngaysinh, String hinhsv,String lop) {
        this.msv = msv;
        this.tensv = tensv;
        this.ngaysinh = ngaysinh;
        this.hinhsv = hinhsv;
        this.lop = lop;
    }

    public String getMsv() {
        return msv;
    }

    public void setMsv(String msv) {
        this.msv = msv;
    }

    public String getTensv() {
        return tensv;
    }

    public void setTensv(String tensv) {
        this.tensv = tensv;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getHinhsv() {
        return hinhsv;
    }

    public void setHinhsv(String hinhsv) {
        this.hinhsv = hinhsv;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }
}
