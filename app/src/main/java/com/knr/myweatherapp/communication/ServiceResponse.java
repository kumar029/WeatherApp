package com.knr.myweatherapp.communication;

/**
 * Created by Kumar on 26-01-2018.
 */

public class ServiceResponse {
    private int code;
    private Object data;
    private ServiceError ServiceError;

    public ServiceResponse(int code, Object response) {
        this.code = code;
        this.data = response;
    }

    public ServiceResponse(ServiceError ServiceError) {
        this.ServiceError = ServiceError;
    }

    public ServiceResponse(Object response) {
        this.data = response;
    }

    public int getCode() {
        return code;
    }

    public ServiceError getServiceError() {
        return ServiceError;
    }

    public Object getData() {

        return data;
    }
}
