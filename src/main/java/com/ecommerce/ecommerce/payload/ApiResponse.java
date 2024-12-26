package com.ecommerce.ecommerce.payload;

public class ApiResponse {
    private String message;
    private Boolean success;
    private Object data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(Boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public ApiResponse(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(Object data) {
        this.data = data;
    }

    public static ApiResponse success(Object data) {
        return new ApiResponse(data);
    }

    public static ApiResponse success(Boolean success, String message, Object data) {
        return new ApiResponse(true, message, data);
    }

    public static ApiResponse success(Boolean success, String message) {
        return new ApiResponse(true, message);
    }

    public static ApiResponse fail(Boolean success, String message) {
        return new ApiResponse(false, message);
    }

    public static ApiResponse fail(Boolean success, Object data) {
        return new ApiResponse(false, data);
    }
}
