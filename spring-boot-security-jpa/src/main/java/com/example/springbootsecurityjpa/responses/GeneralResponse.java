package com.example.springbootsecurityjpa.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;

/**
 * @DATA: 2020/12/3
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class GeneralResponse<T> {
    private int statusCode;
    private String message;
    private T data;

    public GeneralResponse(){}

    public GeneralResponse(int statusCode) {
        this.statusCode = statusCode;
    }

    public GeneralResponse(String message){
        this.message = message;
    }

    public GeneralResponse(int statusCode, String message) {
        this(statusCode);
        this.message = message;
    }

    public GeneralResponse(int statusCode, String message, T data){
        this(statusCode, message);
        this.data = data;
    }
}
