package com.realestate.entity;

/**
 * Loai dich vu bat dong san. Luu trong CSDL theo TEN ENUM (DAT / NHA_VA_DAT),
 * hien thi cho nguoi dung theo nhan tieng Viet (getNhan()).
 */
public enum LoaiDichVu {

    DAT("Đất"),
    NHA_VA_DAT("Nhà và đất");

    private final String nhan;

    LoaiDichVu(String nhan) {
        this.nhan = nhan;
    }

    public String getNhan() {
        return nhan;
    }
}
