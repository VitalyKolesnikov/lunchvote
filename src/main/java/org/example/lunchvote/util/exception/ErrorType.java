package org.example.lunchvote.util.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {
    APP_ERROR("Application error", HttpStatus.INTERNAL_SERVER_ERROR),
    //  http://stackoverflow.com/a/22358422/548473
    DATA_NOT_FOUND("Data not found", HttpStatus.UNPROCESSABLE_ENTITY),
    DATA_ERROR("Data error", HttpStatus.CONFLICT),
    VALIDATION_ERROR("Validation error", HttpStatus.UNPROCESSABLE_ENTITY),
    VOTING_ERROR("Voting error", HttpStatus.I_AM_A_TEAPOT),
    WRONG_REQUEST("Wrong request", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final HttpStatus status;

    ErrorType(String errorCode, HttpStatus status) {
        this.errorCode = errorCode;
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}