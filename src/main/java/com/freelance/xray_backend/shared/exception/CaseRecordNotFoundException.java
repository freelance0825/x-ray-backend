package com.freelance.xray_backend.shared.exception;

public class CaseRecordNotFoundException extends RuntimeException{
    public CaseRecordNotFoundException(String message) {
        super(message);
    }
}
