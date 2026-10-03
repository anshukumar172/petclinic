package com.petclinic.vet.exception;

public class VetAlreadyExistsException extends RuntimeException {

    public VetAlreadyExistsException(String message) {
        super(message);
    }
}