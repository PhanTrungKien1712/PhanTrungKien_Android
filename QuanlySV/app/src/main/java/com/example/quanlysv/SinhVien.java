package com.example.quanlysv;

public class SinhVien {
    public int masv;
    public String hoten;
    public String namsinh;
    public String dienthoai;
    public byte[] img;
    public SinhVien(int masv, String hoten, String namsinh, String dienthoai, byte[] img) {
        this.masv = masv;
        this.hoten = hoten;
        this.namsinh = namsinh;
        this.dienthoai = dienthoai;
        this.img=img;
    }

}
