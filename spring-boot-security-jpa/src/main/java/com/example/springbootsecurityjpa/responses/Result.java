package com.example.springbootsecurityjpa.responses;

import lombok.Getter;
import lombok.Setter;

/**
 * @DATA: 2020/12/3
 */

@Getter
public enum  Result {
    SUCCESS(1000,"Success"),
    FAILED(2000,"Failed"),
    ERROR(5000, "error");

    private int statusCode;
    private String message;
    Result(int statusCode,String message){
        this.statusCode = statusCode;
        this.message = message;
    }
}
