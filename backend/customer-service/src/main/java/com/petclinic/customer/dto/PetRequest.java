package com.petclinic.customer.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class PetRequest {

    @NotBlank(message = "Pet Name is required")
    private String petName;

    @NotBlank(message = "Pet Type is required")
    private String petType;

    @NotBlank(message = "Breed is required")
    private String breed;

    @Min(value = 0, message = "Age cannot be negative")
    private Integer age;

    private Long customerId;

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}