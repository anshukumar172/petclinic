package com.petclinic.customer.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.petclinic.customer.dto.CustomerRequest;
import com.petclinic.customer.exception.CustomerAlreadyExistsException;
import com.petclinic.customer.exception.ResourceNotFoundException;
import com.petclinic.customer.model.Customer;
import com.petclinic.customer.repository.CustomerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private CustomerRequest request;

    @BeforeEach
    void setUp() {

        customer = new Customer();
        customer.setCustomerId(1L);
        customer.setFirstName("Anshu");
        customer.setLastName("Kumar");
        customer.setEmail("anshu@gmail.com");
        customer.setPhoneNumber("9999999999");
        customer.setAddress("Bangalore");

        request = new CustomerRequest();
        request.setFirstName("Anshu");
        request.setLastName("Kumar");
        request.setEmail("anshu@gmail.com");
        request.setPhoneNumber("9999999999");
        request.setAddress("Bangalore");
    }

    @Test
    void testCreateCustomerSuccess() {

        when(customerRepository.existsByEmail(
                request.getEmail()))
                .thenReturn(false);

        String response =
                customerService.createCustomer(request);

        assertEquals(
                "Customer Created Successfully",
                response);

        verify(customerRepository, times(1))
                .save(any(Customer.class));
    }

    @Test
    void testCreateCustomerAlreadyExists() {

        when(customerRepository.existsByEmail(
                request.getEmail()))
                .thenReturn(true);

        assertThrows(
                CustomerAlreadyExistsException.class,
                () -> customerService.createCustomer(request));

        verify(customerRepository, never())
                .save(any(Customer.class));
    }

    @Test
    void testGetAllCustomers() {

        List<Customer> customers =
                Arrays.asList(customer);

        when(customerRepository.findAll())
                .thenReturn(customers);

        List<Customer> result =
                customerService.getAllCustomers();

        assertEquals(1, result.size());
        assertEquals("Anshu",
                result.get(0).getFirstName());
    }

    @Test
    void testGetCustomerByIdSuccess() {

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        Customer result =
                customerService.getCustomerById(1L);

        assertNotNull(result);
        assertEquals(1L,
                result.getCustomerId());
    }

    @Test
    void testGetCustomerByIdNotFound() {

        when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> customerService.getCustomerById(1L));
    }

    @Test
    void testUpdateCustomerSuccess() {

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        String response =
                customerService.updateCustomer(
                        1L, request);

        assertEquals(
                "Customer Updated Successfully",
                response);

        verify(customerRepository,
                times(1))
                .save(customer);
    }

    @Test
    void testUpdateCustomerNotFound() {

        when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> customerService
                        .updateCustomer(1L, request));
    }

    @Test
    void testDeleteCustomerSuccess() {

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        String response =
                customerService.deleteCustomer(1L);

        assertEquals(
                "Customer Deleted Successfully",
                response);

        verify(customerRepository,
                times(1))
                .delete(customer);
    }

    @Test
    void testDeleteCustomerNotFound() {

        when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> customerService.deleteCustomer(1L));
    }
}