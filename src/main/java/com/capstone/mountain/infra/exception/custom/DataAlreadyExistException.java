package com.capstone.mountain.infra.exception.custom;

public class DataAlreadyExistException extends RuntimeException{
    public DataAlreadyExistException(String message) {
        super(message);
    }
}
