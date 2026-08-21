package com.realestate.service.impl;

import com.realestate.dto.KhachHangForm;
import com.realestate.entity.KhachHang;
import com.realestate.repository.GiaoDichRepository;
import com.realestate.repository.KhachHangRepository;
import com.realestate.service.KhachHangService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class KhachHangServiceImpl implements KhachHangService {

    // Ma khach hang: chu+so, khong dau cach, toi da 20 ky tu (khop voi @Column length=20)
    private static final Pattern MA_KHACH_HANG_PATTERN = Pattern.compile("^[A-Za-z0-9\\-]{2,20}$");
    private static final Pattern SO_DIEN_THOAI_PATTERN = Pattern.compile("^0\\d{9,10}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    private final KhachHangRepository khachHangRepository;
    private final GiaoDichRepository giaoDichRepository;

    public KhachHangServiceImpl(KhachHangRepository khachHangRepository, GiaoDichRepository giaoDichRepository) {
        this.khachHangRepository = khachHangRepository;
        this.giaoDichRepository = giaoDichRepository;
    }

    @Override
    public List<KhachHang> findAll() {
        return khachHangRepository.findAll();
    }

    @Override
    public Map<String, String> validate(KhachHangForm form) {
        Map<String, String> errors = new LinkedHashMap<>();

        // --- Ma khach hang: bat buoc, dung dinh dang, khong trung ---
        String ma = trim(form.getMaKhachHang());
        if (ma.isEmpty()) {
            errors.put("maKhachHang", "Mã khách hàng không được để trống.");
        } else if (!MA_KHACH_HANG_PATTERN.matcher(ma).matches()) {
            errors.put("maKhachHang", "Mã khách hàng chỉ gồm chữ, số, dấu gạch ngang, tối đa 20 ký tự.");
        } else if (khachHangRepository.existsById(ma)) {
            errors.put("maKhachHang", "Mã khách hàng này đã tồn tại.");
        }

        // --- Ten khach hang: bat buoc ---
        String ten = trim(form.getTenKhachHang());
        if (ten.isEmpty()) {
            errors.put("tenKhachHang", "Tên khách hàng không được để trống.");
        }

        // --- So dien thoai: khong bat buoc, nhung neu nhap thi phai dung dinh dang VN ---
        String sdt = trim(form.getSoDienThoai());
        if (!sdt.isEmpty() && !SO_DIEN_THOAI_PATTERN.matcher(sdt).matches()) {
            errors.put("soDienThoai", "Số điện thoại không hợp lệ (VD: 0912345678).");
        }

        // --- Email: khong bat buoc, nhung neu nhap thi phai dung dinh dang ---
        String email = trim(form.getEmail());
        if (!email.isEmpty() && !EMAIL_PATTERN.matcher(email).matches()) {
            errors.put("email", "Email không hợp lệ.");
        }

        return errors;
    }

    @Override
    public void save(KhachHangForm form) {
        KhachHang kh = new KhachHang();
        kh.setMaKhachHang(trim(form.getMaKhachHang()));
        kh.setTenKhachHang(trim(form.getTenKhachHang()));
        String sdt = trim(form.getSoDienThoai());
        kh.setSoDienThoai(sdt.isEmpty() ? null : sdt);
        String email = trim(form.getEmail());
        kh.setEmail(email.isEmpty() ? null : email);
        khachHangRepository.save(kh);
    }

    @Override
    public void delete(String maKhachHang) {
        khachHangRepository.deleteById(maKhachHang);
    }

    @Override
    public boolean isDangSuDung(String maKhachHang) {
        return giaoDichRepository.existsByKhachHang_MaKhachHang(maKhachHang);
    }

    private String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
