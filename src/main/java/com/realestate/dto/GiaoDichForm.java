package com.realestate.dto;

/**
 * DTO nhan du lieu tho tu form "Them moi giao dich". Chu y: TAT CA field deu de String
 * (kho khong dung LocalDate/BigDecimal truc tiep) de tranh loi bind-time cua Spring khi
 * nguoi dung nhap sai dinh dang / bo trong - viec validate chi tiet (bat buoc, dung dinh
 * dang, dung quy tac nghiep vu) duoc thuc hien thu cong trong GiaoDichService#validate,
 * cho phep tra ve thong bao loi tieng Viet chinh xac cho tung truong.
 */
public class GiaoDichForm {

    private String maGiaoDich;
    private String maKhachHang;
    private String loaiDichVu;
    private String ngayGiaoDich; // dinh dang yyyy-MM-dd (tu input type="date")
    private String donGia;
    private String dienTich;

    public GiaoDichForm() {
    }

    public String getMaGiaoDich() {
        return maGiaoDich;
    }

    public void setMaGiaoDich(String maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getLoaiDichVu() {
        return loaiDichVu;
    }

    public void setLoaiDichVu(String loaiDichVu) {
        this.loaiDichVu = loaiDichVu;
    }

    public String getNgayGiaoDich() {
        return ngayGiaoDich;
    }

    public void setNgayGiaoDich(String ngayGiaoDich) {
        this.ngayGiaoDich = ngayGiaoDich;
    }

    public String getDonGia() {
        return donGia;
    }

    public void setDonGia(String donGia) {
        this.donGia = donGia;
    }

    public String getDienTich() {
        return dienTich;
    }

    public void setDienTich(String dienTich) {
        this.dienTich = dienTich;
    }
}
