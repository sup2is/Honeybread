package com.whatsub.honeybread.core.infra.errors;

import lombok.Value;
import org.springframework.validation.Errors;

import java.util.List;

@Value
public class ErrorResponse {
    String message;
    int status;
    List<ErrorField> errors;
    String code;

    static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    static ErrorResponse of(ErrorCode errorCode, Errors errors) {
        return new ErrorResponse(errorCode, ErrorField.of(errors));
    }

    static ErrorResponse of(ErrorCode errorCode, List<ErrorField> errorFields) {
        return new ErrorResponse(errorCode, errorFields);
    }

    private ErrorResponse(String message, int status, List<ErrorField> errors, String code) {
        this.message = message;
        this.status = status;
        this.errors = errors;
        this.code = code;
    }

    private ErrorResponse(ErrorCode errorCode, List<ErrorField> errorFields) {
        this(errorCode.getMessage(), errorCode.getStatus().value(), errorFields, errorCode.getCode());
    }

    private ErrorResponse(ErrorCode errorCode) {
        this(errorCode.getMessage(), errorCode.getStatus().value(), List.of(), errorCode.getCode());
    }
}
