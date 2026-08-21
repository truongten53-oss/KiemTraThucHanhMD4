-- Du lieu mau. Dung INSERT ... ON DUPLICATE KEY UPDATE (upsert theo khoa chinh, cu phap MySQL)
-- de chay lai nhieu lan (moi lan start app, vi spring.sql.init.mode=always) khong bi loi trung khoa chinh.

INSERT INTO khach_hang (ma_khach_hang, ten_khach_hang, so_dien_thoai, email) VALUES ('KH-0001', 'Nguyễn Văn A', '0905973155', 'nguyenvana@gmail.com')
    ON DUPLICATE KEY UPDATE ten_khach_hang = VALUES(ten_khach_hang), so_dien_thoai = VALUES(so_dien_thoai), email = VALUES(email);
INSERT INTO khach_hang (ma_khach_hang, ten_khach_hang, so_dien_thoai, email) VALUES ('KH-0002', 'Nguyễn Văn B', '0905123456', 'nguyenvanb@gmail.com')
    ON DUPLICATE KEY UPDATE ten_khach_hang = VALUES(ten_khach_hang), so_dien_thoai = VALUES(so_dien_thoai), email = VALUES(email);
INSERT INTO khach_hang (ma_khach_hang, ten_khach_hang, so_dien_thoai, email) VALUES ('KH-0003', 'Nguyễn Văn C', '0909888777', 'nguyenvanc@gmail.com')
    ON DUPLICATE KEY UPDATE ten_khach_hang = VALUES(ten_khach_hang), so_dien_thoai = VALUES(so_dien_thoai), email = VALUES(email);
INSERT INTO khach_hang (ma_khach_hang, ten_khach_hang, so_dien_thoai, email) VALUES ('KH-0004', 'Trần Thị D', '0912345678', 'tranthid@gmail.com')
    ON DUPLICATE KEY UPDATE ten_khach_hang = VALUES(ten_khach_hang), so_dien_thoai = VALUES(so_dien_thoai), email = VALUES(email);

INSERT INTO giao_dich (ma_giao_dich, ma_khach_hang, ngay_giao_dich, loai_dich_vu, don_gia, dien_tich) VALUES ('MGD-0001', 'KH-0001', '2020-12-12', 'NHA_VA_DAT', 2000000, 100)
    ON DUPLICATE KEY UPDATE ma_khach_hang = VALUES(ma_khach_hang), ngay_giao_dich = VALUES(ngay_giao_dich), loai_dich_vu = VALUES(loai_dich_vu), don_gia = VALUES(don_gia), dien_tich = VALUES(dien_tich);
INSERT INTO giao_dich (ma_giao_dich, ma_khach_hang, ngay_giao_dich, loai_dich_vu, don_gia, dien_tich) VALUES ('MGD-0002', 'KH-0002', '2020-12-12', 'DAT', 3000000, 200)
    ON DUPLICATE KEY UPDATE ma_khach_hang = VALUES(ma_khach_hang), ngay_giao_dich = VALUES(ngay_giao_dich), loai_dich_vu = VALUES(loai_dich_vu), don_gia = VALUES(don_gia), dien_tich = VALUES(dien_tich);
INSERT INTO giao_dich (ma_giao_dich, ma_khach_hang, ngay_giao_dich, loai_dich_vu, don_gia, dien_tich) VALUES ('MGD-0003', 'KH-0003', '2020-12-12', 'NHA_VA_DAT', 5000000, 100)
    ON DUPLICATE KEY UPDATE ma_khach_hang = VALUES(ma_khach_hang), ngay_giao_dich = VALUES(ngay_giao_dich), loai_dich_vu = VALUES(loai_dich_vu), don_gia = VALUES(don_gia), dien_tich = VALUES(dien_tich);
