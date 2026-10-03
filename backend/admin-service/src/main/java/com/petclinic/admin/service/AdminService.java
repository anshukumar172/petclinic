package com.petclinic.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.petclinic.admin.dto.AdminRequest;
import com.petclinic.admin.entity.Admin;
import com.petclinic.admin.exception.AdminAlreadyExistsException;
import com.petclinic.admin.exception.ResourceNotFoundException;
import com.petclinic.admin.repository.AdminRepository;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    // Create Admin
    public String createAdmin(AdminRequest request) {

        if (adminRepository.existsByUsername(request.getUsername())) {
            throw new AdminAlreadyExistsException(
                    "Username already exists");
        }

        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new AdminAlreadyExistsException(
                    "Email already exists");
        }

        Admin admin = new Admin();

        admin.setUsername(request.getUsername());
        admin.setFirstName(request.getFirstName());
        admin.setLastName(request.getLastName());
        admin.setEmail(request.getEmail());
        admin.setMobileNumber(request.getMobileNumber());

        adminRepository.save(admin);

        return "Admin Created Successfully";
    }

    // Get All Admins
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // Get Admin By Id
    public Admin getAdminById(Long id) {

        return adminRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Admin not found with id: " + id));
    }

    // Update Admin
    public String updateAdmin(Long id, AdminRequest request) {

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Admin not found with id: " + id));

        admin.setUsername(request.getUsername());
        admin.setFirstName(request.getFirstName());
        admin.setLastName(request.getLastName());
        admin.setEmail(request.getEmail());
        admin.setMobileNumber(request.getMobileNumber());

        adminRepository.save(admin);

        return "Admin Updated Successfully";
    }



// Delete Admin
public String deleteAdmin(Long id) {

    Admin admin = adminRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Admin not found with id: " + id));

    adminRepository.delete(admin);

    return "Admin Deleted Successfully";
}

    
}// Delete