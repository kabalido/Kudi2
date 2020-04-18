package com.everaldo.kudi2.util;

public class ResponseResult {

    private int status;
    private boolean isSuccess;
    private String errMessage;
    private User user;

    public ResponseResult(int status, boolean isSuccess, String errMessage, User user) {
        this.status = status;
        this.errMessage = errMessage;
        this.user = user;
        this.isSuccess = isSuccess;

    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return errMessage;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "status=" + status +
                ", message='" + errMessage + '\'' +
                '}';
    }

    public boolean isOperationSuccessful(){
        return this.isSuccess;
    }

    public User getUser(){
        return this.user;
    }
}
