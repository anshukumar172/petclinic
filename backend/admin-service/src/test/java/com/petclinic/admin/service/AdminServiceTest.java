package com.petclinic.admin.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.petclinic.admin.dto.AdminRequest;
import com.petclinic.admin.entity.Admin;
import com.petclinic.admin.exception.AdminAlreadyExistsException;
import com.petclinic.admin.exception.ResourceNotFoundException;
import com.petclinic.admin.repository.AdminRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    private Admin admin;
    private AdminRequest request;

    @BeforeEach
    void setUp() {

        admin = new Admin();
        admin.setAdminId(1L);
        admin.setUsername("admin1");
        admin.setFirstName("Anshu");
        admin.setLastName("Kumar");
        admin.setEmail("admin@gmail.com");
        admin.setMobileNumber("9999999999");

        request = new AdminRequest();
        request.setUsername("admin1");
        request.setFirstName("Anshu");
        request.setLastName("Kumar");
        request.setEmail("admin@gmail.com");
        request.setMobileNumber("9999999999");
    }

    @Test
    void testCreateAdminSuccess() {

        when(adminRepository.existsByUsername(
                request.getUsername()))
                .thenReturn(false);

        when(adminRepository.existsByEmail(
                request.getEmail()))
                .thenReturn(false);

        String result =
                adminService.createAdmin(request);

        assertEquals(
                "Admin Created Successfully",
                result);

        verify(adminRepository, times(1))
                .save(any(Admin.class));
    }

    @Test
    void testCreateAdminUsernameAlreadyExists() {

        when(adminRepository.existsByUsername(
                request.getUsername()))
                .thenReturn(true);

        assertThrows(
                AdminAlreadyExistsException.class,
                () -> adminService.createAdmin(request));

        verify(adminRepository, never())
                .save(any(Admin.class));
    }

    @Test
    void testCreateAdminEmailAlreadyExists() {

        when(adminRepository.existsByUsername(
                request.getUsername()))
                .thenReturn(false);

        when(adminRepository.existsByEmail(
                request.getEmail()))
                .thenReturn(true);

        assertThrows(
                AdminAlreadyExistsException.class,
                () -> adminService.createAdmin(request));

        verify(adminRepository, never())
                .save(any(Admin.class));
    }

    @Test
    void testGetAllAdmins() {

        List<Admin> admins =
                Arrays.asList(admin);

        when(adminRepository.findAll())
                .thenReturn(admins);

        List<Admin> result =
                adminService.getAllAdmins();

        assertEquals(1,
                result.size());

        assertEquals(
                "admin1",
                result.get(0).getUsername());
    }

    @Test
    void testGetAdminByIdSuccess() {

        when(adminRepository.findById(1L))
                .thenReturn(Optional.of(admin));

        Admin result =
                adminService.getAdminById(1L);

        assertNotNull(result);

        assertEquals(
                1L,
                result.getAdminId());

        assertEquals(
                "admin1",
                result.getUsername());
    }

    @Test
    void testGetAdminByIdNotFound() {

        when(adminRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> adminService.getAdminById(1L));
    }

    @Test
    void testUpdateAdminSuccess() {

        when(adminRepository.findById(1L))
                .thenReturn(Optional.of(admin));

        String result =
                adminService.updateAdmin(
                        1L,
                        request);

        assertEquals(
                "Admin Updated Successfully",
                result);

        verify(adminRepository,
                times(1))
                .save(admin);
    }

    @Test
    void testUpdateAdminNotFound() {

        when(adminRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> adminService.updateAdmin(
                        1L,
                        request));
    }

    @Test
    void testDeleteAdminSuccess() {

        when(adminRepository.findById(1L))
                .thenReturn(Optional.of(admin));

        String result =
                adminService.deleteAdmin(1L);

        assertEquals(
                "Admin Deleted Successfully",
                result);

        verify(adminRepository,
                times(1))
                .delete(admin);
    }

    @Test
    void testDeleteAdminNotFound() {

        when(adminRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> adminService.deleteAdmin(1L));
    }
}