package com.realestate.service;

import com.realestate.dto.GiaoDichForm;
import com.realestate.entity.GiaoDich;
import com.realestate.entity.LoaiDichVu;

import java.util.List;
import java.util.Map;

public interface GiaoDichService {

    List<GiaoDich> search(String tenKhachHang, LoaiDichVu loaiDichVu);

    GiaoDich findById(String maGiaoDich);

    void delete(String maGiaoDich);

    /**
     * Kiem tra toan bo du lieu form theo yeu cau nghiep vu (bat buoc nhap, dinh dang
     * ma giao dich, ngay giao dich tuong lai, don gia > 500.000, dien tich > 20...).
     *
     * @return Map rong neu hop le; nguoc lai Map (ten_truong -> thong bao loi) de hien
     *         thi ngay canh tung truong tren form.
     */
    Map<String, String> validate(GiaoDichForm form);

    /**
     * Luu giao dich moi. CHI goi ham nay sau khi validate(form) tra ve rong.
     */
    void save(GiaoDichForm form);
}
