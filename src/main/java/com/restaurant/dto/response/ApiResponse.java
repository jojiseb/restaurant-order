package com.restaurant.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T object;
    private T objects;
    private Integer statusCode;
    private String status;
    private Integer totalResultCount;
    private String error;

    public ApiResponse() {
        this.statusCode = HttpStatus.OK.value();
        this.success = true;
        this.status = HttpStatus.OK.name();
    }

    public ApiResponse(T object) {
        this.success = true;
        this.object = object;
        this.statusCode = HttpStatus.OK.value();
        this.status = HttpStatus.OK.name();
        this.message = "Request is Successful";
    }

    public ApiResponse(T objects, Integer totalResultCount) {
        this.success = true;
        this.objects = objects;
        this.totalResultCount = totalResultCount;
        this.statusCode = HttpStatus.OK.value();
        this.status = HttpStatus.OK.name();
        this.message = "Request processed successfully";
    }

    public ApiResponse(HttpStatus httpStatus, String error) {
        this.success = false;
        this.error = error;
        this.statusCode = httpStatus.value();
        this.status = httpStatus.name();
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(data);
    }

    public static <T> ApiResponse<T> ok(T results, Integer totalResults) {
        return new ApiResponse<>(results, totalResults);
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String error) {
        return new ApiResponse<>(status, error);
    }

    public static <T> ApiResponse<T> notFound(String error) {
        return new ApiResponse<>(HttpStatus.NOT_FOUND, error);
    }

    public static <T> ApiResponse<T> badRequest(String error) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, error);
    }

}
