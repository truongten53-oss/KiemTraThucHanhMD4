package com.realestate.controller;

import com.realestate.dto.GiaoDichForm;
import com.realestate.entity.GiaoDich;
import com.realestate.entity.LoaiDichVu;
import com.realestate.repository.KhachHangRepository;
import com.realestate.service.GiaoDichService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class GiaoDichController {

    private final GiaoDichService giaoDichService;
    private final KhachHangRepository khachHangRepository;

    public GiaoDichController(GiaoDichService giaoDichService, KhachHangRepository khachHangRepository) {
        this.giaoDichService = giaoDichService;
        this.khachHangRepository = khachHangRepository;
    }

    // ------------------------------------------------------------------
    // 1. Danh sach giao dich + tim kiem (theo ten khach hang / loai dich vu / ca hai)
    // ------------------------------------------------------------------
    @GetMapping("/")
    public String list(@RequestParam(required = false) String tenKhachHang,
                        @RequestParam(required = false) String loaiDichVu,
                        Model model) {
        LoaiDichVu loai = parseLoaiDichVu(loaiDichVu);
        List<GiaoDich> rows = giaoDichService.search(tenKhachHang, loai);

        model.addAttribute("rows", rows);
        model.addAttribute("loaiDichVuOptions", LoaiDichVu.values());
        model.addAttribute("filterTen", tenKhachHang);
        model.addAttribute("filterLoai", loaiDichVu);
        return "list";
    }

    // ------------------------------------------------------------------
    // 2. Them moi giao dich
    // ------------------------------------------------------------------
    @GetMapping("/giao-dich/them-moi")
    public String addForm(Model model) {
        model.addAttribute("form", new GiaoDichForm());
        model.addAttribute("customers", khachHangRepository.findAll());
        model.addAttribute("loaiDichVuOptions", LoaiDichVu.values());
        model.addAttribute("errors", Collections.emptyMap());
        return "add";
    }

    @PostMapping("/giao-dich/them-moi")
    public String addSubmit(@ModelAttribute("form") GiaoDichForm form,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        Map<String, String> errors = giaoDichService.validate(form);

        if (!errors.isEmpty()) {
            model.addAttribute("customers", khachHangRepository.findAll());
            model.addAttribute("loaiDichVuOptions", LoaiDichVu.values());
            model.addAttribute("errors", errors);
            model.addAttribute("formError", "Vui lòng kiểm tra lại thông tin đã nhập.");
            return "add";
        }

        giaoDichService.save(form);
        redirectAttributes.addFlashAttribute(
                "successMessage", "Thêm mới giao dịch " + form.getMaGiaoDich() + " thành công!");
        return "redirect:/";
    }

    // ------------------------------------------------------------------
    // 3. Chi tiet + xoa giao dich
    // ------------------------------------------------------------------
    @GetMapping("/giao-dich/{maGiaoDich}")
    public String detail(@PathVariable String maGiaoDich, Model model, RedirectAttributes redirectAttributes) {
        GiaoDich gd = giaoDichService.findById(maGiaoDich);
        if (gd == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy giao dịch " + maGiaoDich + ".");
            return "redirect:/";
        }
        model.addAttribute("gd", gd);
        return "detail";
    }

    @PostMapping("/giao-dich/{maGiaoDich}/xoa")
    public String delete(@PathVariable String maGiaoDich, RedirectAttributes redirectAttributes) {
        giaoDichService.delete(maGiaoDich);
        redirectAttributes.addFlashAttribute("successMessage", "Đã xóa giao dịch " + maGiaoDich + ".");
        return "redirect:/";
    }

    private LoaiDichVu parseLoaiDichVu(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        try {
            return LoaiDichVu.valueOf(raw);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
