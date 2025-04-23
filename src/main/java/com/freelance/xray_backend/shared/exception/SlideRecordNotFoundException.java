package com.freelance.xray_backend.shared.exception;

public class SlideRecordNotFoundException extends RuntimeException{
    public SlideRecordNotFoundException(String message) {
        super(message);
    }
}