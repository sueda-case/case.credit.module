package com.example.credit.module.model;

import lombok.Getter;
import lombok.Setter;


public class Response {
    private String code;
    private String message;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
