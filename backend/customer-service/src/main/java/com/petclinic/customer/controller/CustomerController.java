package com.petclinic.customer.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.petclinic.customer.dto.CustomerRequest;
import com.petclinic.customer.model.Customer;
import com.petclinic.customer.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Create Customer



    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(
            @Valid @RequestBody CustomerRequest request) {

        return ResponseEntity.ok(
                customerService.createCustomer(request));
    }
    // Get All Customers
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {

        return ResponseEntity.ok(
                customerService.getAllCustomers());
    }

    // Get Customer By Id
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                customerService.getCustomerById(id));
    }

    // Update Customer
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequest request) {

        return ResponseEntity.ok(
                customerService.updateCustomer(id, request));
    }

    // Delete Customer
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                customerService.deleteCustomer(id));
    }
    
}