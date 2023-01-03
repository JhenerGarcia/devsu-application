package com.devsu.configuration;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.devsu.dto.ApiData;
import com.devsu.dto.ApiResult;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler({ ApiException.class, Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult resolveException(Exception ex) {
        log.error("Exception", ex);
        if (ex instanceof ApiException)
            return ((ApiException) ex).getApiResult();
        return new ApiResult("500", "Internal Server Error", LocalDateTime.now(),
                new ApiData("Exception", ex.getMessage()));
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult resolveDataIntegrityViolationException(Exception ex) {
        log.error("Exception", ex);
        if (ex instanceof ApiException)
            return ((ApiException) ex).getApiResult();
        return new ApiResult("50090", "Data Integrity Violation", LocalDateTime.now(),
                new ApiData("Exception", ex.getMessage()));
    }

}
