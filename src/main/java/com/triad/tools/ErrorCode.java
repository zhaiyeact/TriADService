package com.triad.tools;

/**
 * Created by zhuoying on 2015/10/12.
 */
public enum ErrorCode {
    SUCCESS(0,"SUCCESS"),
    SOCKET_ERROR(1,"SOCKET ERROR"),
    EMPTY_QUERY(2,"EMPTY QUERY FOUND")
    ;


    private int code;

    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ErrorCode(int code,String message){
        this.code = code;
        this.message = message;
    }

}
