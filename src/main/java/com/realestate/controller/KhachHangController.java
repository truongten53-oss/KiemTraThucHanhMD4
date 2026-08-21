package com.realestate.controller;

import com.realestate.dto.KhachHangForm;
import com.realestate.service.KhachHangService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping("/khach-hang")
public class KhachHangController {

    private final KhachHangService khachHangService;

    public KhachHangController(KhachHangService khachHangService) {
        this.khachHangService = khachHangService;
    }

    // ------------------------------------------------------------------
    // 1. Danh sach khach hang
    // ------------------------------------------------------------------
    @GetMapping
    public String list(Model model) {
        model.addAttribute("customers", khachHangService.findAll());
        return "khach-hang-list";
    }

    // ------------------------------------------------------------------
    // 2. Them moi khach hang. Tham so "redirectTo" (tuy chon): khi mo tu nut
    // "+ Khach hang moi" ngay trong form tao giao dich, sau khi luu xong se
    // quay lai dung trang do thay vi ve trang danh sach khach hang.
    // ------------------------------------------------------------------
    @GetMapping("/them-moi")
    public String addForm(@RequestParam(required = false) String redirectTo, Model model) {
        model.addAttribute("form", new KhachHangForm());
        model.addAttribute("errors", Collections.emptyMap());
        model.addAttribute("redirectTo", redirectTo);
        return "khach-hang-add";
    }

    @PostMapping("/them-moi")
    public String addSubmit(@ModelAttribute("form") KhachHangForm form,
                             @RequestParam(required = false) String redirectTo,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        Map<String, String> errors = khachHangService.validate(form);

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("redirectTo", redirectTo);
            model.addAttribute("formError", "Vui lòng kiểm tra lại thông tin đã nhập.");
            return "khach-hang-add";
        }

        khachHangService.save(form);
        redirectAttributes.addFlashAttribute(
                "successMessage", "Thêm mới khách hàng " + form.getMaKhachHang() + " thành công!");

        if (redirectTo != null && !redirectTo.isBlank()) {
            return "redirect:" + redirectTo;
        }
        return "redirect:/khach-hang";
    }

    // ------------------------------------------------------------------
    // 3. Xoa khach hang (chan neu khach hang dang co giao dich)
    // ------------------------------------------------------------------
    @PostMapping("/{maKhachHang}/xoa")
    public String delete(@PathVariable String maKhachHang, RedirectAttributes redirectAttributes) {
        if (khachHangService.isDangSuDung(maKhachHang)) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Không thể xóa khách hàng " + maKhachHang + " vì đang có giao dịch liên quan.");
            return "redirect:/khach-hang";
        }
        khachHangService.delete(maKhachHang);
        redirectAttributes.addFlashAttribute("successMessage", "Đã xóa khách hàng " + maKhachHang + ".");
        return "redirect:/khach-hang";
    }
}
