package com.realestate.service;

import com.realestate.dto.KhachHangForm;
import com.realestate.entity.KhachHang;

import java.util.List;
import java.util.Map;

public interface KhachHangService {
    List<KhachHang> findAll();
    Map<String, String> validate(KhachHangForm form);
    void save(KhachHangForm form);
    void delete(String maKhachHang);
    boolean isDangSuDung(String maKhachHang);
}
