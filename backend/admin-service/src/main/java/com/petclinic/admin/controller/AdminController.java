package com.petclinic.admin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petclinic.admin.dto.AdminRequest;
import com.petclinic.admin.entity.Admin;
import com.petclinic.admin.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Create Admin
    @PostMapping
    public ResponseEntity<String> createAdmin(
            @Valid @RequestBody AdminRequest request) {

        return ResponseEntity.ok(
                adminService.createAdmin(request));
    }

    // Get All Admins
    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {

        return ResponseEntity.ok(
                adminService.getAllAdmins());
    }

    // Get Admin By Id
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                adminService.getAdminById(id));
    }

    // Update Admin
    @PutMapping("/{id}")
    public ResponseEntity<String> updateAdmin(
            @PathVariable Long id,
            @Valid @RequestBody AdminRequest request) {

        return ResponseEntity.ok(
                adminService.updateAdmin(id, request));
    }

    // Delete Admin
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdmin(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                adminService.deleteAdmin(id));
    }
}