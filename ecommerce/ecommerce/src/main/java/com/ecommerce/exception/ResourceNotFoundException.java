package com.ecommerce.exception;

public class ResourceNotFoundException extends RuntimeException { 

	public ResourceNotFoundException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

} 
