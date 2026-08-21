package com.realestate.dto;

/**
 * DTO nhan du lieu tho tu form "Them khach hang moi". Tuong tu GiaoDichForm,
 * de String het de validate thu cong trong KhachHangService#validate.
 */
public class KhachHangForm {

    private String maKhachHang;
    private String tenKhachHang;
    private String soDienThoai;
    private String email;

    public KhachHangForm() {
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
