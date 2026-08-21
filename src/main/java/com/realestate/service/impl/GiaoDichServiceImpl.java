package com.realestate.service.impl;

import com.realestate.dto.GiaoDichForm;
import com.realestate.entity.GiaoDich;
import com.realestate.entity.KhachHang;
import com.realestate.entity.LoaiDichVu;
import com.realestate.repository.GiaoDichRepository;
import com.realestate.repository.KhachHangRepository;
import com.realestate.service.GiaoDichService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class GiaoDichServiceImpl implements GiaoDichService {

    // Ma giao dich phai co dinh dang MGD-XXXX, XXXX la 4 chu so 0-9
    private static final Pattern MA_GIAO_DICH_PATTERN = Pattern.compile("^MGD-\\d{4}$");
    private static final BigDecimal DON_GIA_TOI_THIEU = new BigDecimal("500000");
    private static final double DIEN_TICH_TOI_THIEU = 20.0;

    private final GiaoDichRepository giaoDichRepository;
    private final KhachHangRepository khachHangRepository;

    public GiaoDichServiceImpl(GiaoDichRepository giaoDichRepository, KhachHangRepository khachHangRepository) {
        this.giaoDichRepository = giaoDichRepository;
        this.khachHangRepository = khachHangRepository;
    }

    @Override
    public List<GiaoDich> search(String tenKhachHang, LoaiDichVu loaiDichVu) {
        String ten = (tenKhachHang == null || tenKhachHang.isBlank()) ? null : tenKhachHang.trim();
        return giaoDichRepository.search(ten, loaiDichVu);
    }

    @Override
    public GiaoDich findById(String maGiaoDich) {
        return giaoDichRepository.findById(maGiaoDich).orElse(null);
    }

    @Override
    public void delete(String maGiaoDich) {
        giaoDichRepository.deleteById(maGiaoDich);
    }

    @Override
    public Map<String, String> validate(GiaoDichForm form) {
        Map<String, String> errors = new LinkedHashMap<>();

        // --- Ma giao dich: bat buoc + dung dinh dang MGD-XXXX + khong trung ---
        String maGiaoDich = trim(form.getMaGiaoDich());
        if (maGiaoDich.isEmpty()) {
            errors.put("maGiaoDich", "Mã giao dịch không được để trống.");
        } else if (!MA_GIAO_DICH_PATTERN.matcher(maGiaoDich).matches()) {
            errors.put("maGiaoDich", "Mã giao dịch phải theo định dạng MGD-XXXX (XXXX là 4 chữ số).");
        } else if (giaoDichRepository.existsById(maGiaoDich)) {
            errors.put("maGiaoDich", "Mã giao dịch này đã tồn tại.");
        }

        // --- Khach hang: bat buoc chon, phai ton tai trong bang khach_hang ---
        String maKhachHang = trim(form.getMaKhachHang());
        if (maKhachHang.isEmpty()) {
            errors.put("maKhachHang", "Vui lòng chọn khách hàng.");
        } else if (!khachHangRepository.existsById(maKhachHang)) {
            errors.put("maKhachHang", "Khách hàng không hợp lệ.");
        }

        // --- Loai dich vu: bat buoc chon 1 trong 2 (Dat / Nha va dat) ---
        String loaiDichVuRaw = trim(form.getLoaiDichVu());
        if (loaiDichVuRaw.isEmpty()) {
            errors.put("loaiDichVu", "Vui lòng chọn loại dịch vụ (Đất hoặc Nhà và đất).");
        } else {
            try {
                LoaiDichVu.valueOf(loaiDichVuRaw);
            } catch (IllegalArgumentException ex) {
                errors.put("loaiDichVu", "Loại dịch vụ không hợp lệ.");
            }
        }

        // --- Ngay giao dich: bat buoc, dung dinh dang, phai lon hon hien tai ---
        String ngayRaw = trim(form.getNgayGiaoDich());
        if (ngayRaw.isEmpty()) {
            errors.put("ngayGiaoDich", "Ngày giao dịch không được để trống.");
        } else {
            try {
                LocalDate ngay = LocalDate.parse(ngayRaw); // yyyy-MM-dd (ISO) tu input type=date
                if (!ngay.isAfter(LocalDate.now())) {
                    errors.put("ngayGiaoDich", "Ngày giao dịch phải lớn hơn ngày hiện tại.");
                }
            } catch (DateTimeParseException ex) {
                errors.put("ngayGiaoDich", "Ngày giao dịch không đúng định dạng ngày/tháng/năm.");
            }
        }

        // --- Don gia: bat buoc, phai la so, phai > 500.000 ---
        String donGiaRaw = trim(form.getDonGia());
        if (donGiaRaw.isEmpty()) {
            errors.put("donGia", "Đơn giá không được để trống.");
        } else {
            try {
                BigDecimal donGia = new BigDecimal(donGiaRaw);
                if (donGia.compareTo(DON_GIA_TOI_THIEU) <= 0) {
                    errors.put("donGia", "Đơn giá phải là số lớn hơn 500.000 VND.");
                }
            } catch (NumberFormatException ex) {
                errors.put("donGia", "Đơn giá phải là số.");
            }
        }

        // --- Dien tich: bat buoc, phai la so, phai > 20 ---
        String dienTichRaw = trim(form.getDienTich());
        if (dienTichRaw.isEmpty()) {
            errors.put("dienTich", "Diện tích không được để trống.");
        } else {
            try {
                double dienTich = Double.parseDouble(dienTichRaw);
                if (dienTich <= DIEN_TICH_TOI_THIEU) {
                    errors.put("dienTich", "Diện tích phải là số lớn hơn 20 m2.");
                }
            } catch (NumberFormatException ex) {
                errors.put("dienTich", "Diện tích phải là số.");
            }
        }

        return errors;
    }

    @Override
    public void save(GiaoDichForm form) {
        KhachHang khachHang = khachHangRepository.findById(trim(form.getMaKhachHang()))
                .orElseThrow(() -> new IllegalStateException("Khong tim thay khach hang"));

        GiaoDich gd = new GiaoDich();
        gd.setMaGiaoDich(trim(form.getMaGiaoDich()));
        gd.setKhachHang(khachHang);
        gd.setLoaiDichVu(LoaiDichVu.valueOf(trim(form.getLoaiDichVu())));
        gd.setNgayGiaoDich(LocalDate.parse(trim(form.getNgayGiaoDich())));
        gd.setDonGia(new BigDecimal(trim(form.getDonGia())));
        gd.setDienTich(Double.parseDouble(trim(form.getDienTich())));

        giaoDichRepository.save(gd);
    }

    private String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
