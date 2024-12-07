package com.example.credit.module.utility;

public enum ErrorCodes{
    CUSTOMER_NOT_FOUND("50001", "Customer not found"),
    INSUFICENT_CREDIT("50002", "Insufficient Credit"),
    SUCCESS("200","Success");

    private final String code;
    private final String message;

    ErrorCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}