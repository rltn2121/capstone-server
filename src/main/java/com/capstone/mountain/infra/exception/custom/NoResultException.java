package com.capstone.mountain.infra.exception.custom;

public class NoResultException extends RuntimeException{
    public NoResultException(String message) {
        super(message);
    }
}
