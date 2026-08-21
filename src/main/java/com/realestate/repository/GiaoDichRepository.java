package com.realestate.repository;

import com.realestate.entity.GiaoDich;
import com.realestate.entity.LoaiDichVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GiaoDichRepository extends JpaRepository<GiaoDich, String> {

    /**
     * Tim kiem theo TEN khach hang (tuong doi, khong phan biet hoa/thuong) va/hoac
     * theo loai dich vu. Truyen null cho tham so nao khong loc.
     * "join fetch" de lay luon KhachHang trong cung 1 cau query (tranh N+1 query).
     */
    @Query("select gd from GiaoDich gd join fetch gd.khachHang kh " +
            "where (:tenKhachHang is null or lower(kh.tenKhachHang) like lower(concat('%', :tenKhachHang, '%'))) " +
            "and (:loaiDichVu is null or gd.loaiDichVu = :loaiDichVu) " +
            "order by gd.maGiaoDich")
    List<GiaoDich> search(@Param("tenKhachHang") String tenKhachHang,
                           @Param("loaiDichVu") LoaiDichVu loaiDichVu);

    // Kiem tra khach hang co dang duoc tham chieu boi giao dich nao khong,
    // dung de chan xoa khach hang gay loi rang buoc khoa ngoai (FK constraint).
    boolean existsByKhachHang_MaKhachHang(String maKhachHang);
}
