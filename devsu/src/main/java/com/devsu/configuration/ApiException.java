package com.devsu.configuration;

import com.devsu.dto.ApiResult;

public class ApiException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = -1465798667170192447L;
    private ApiResult apiResult;

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(Throwable cause, ApiResult apiResult) {
        super(cause);
        this.apiResult = apiResult;
    }

    public ApiException(ApiResult apiResult) {
        super(apiResult.getMessage());
        this.apiResult = apiResult;
    }

    public ApiResult getApiResult() {
        return apiResult;
    }

    public void setApiResult(ApiResult apiResult) {
        this.apiResult = apiResult;
    }

}