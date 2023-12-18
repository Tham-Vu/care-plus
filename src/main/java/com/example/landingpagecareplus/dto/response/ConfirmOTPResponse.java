package com.example.landingpagecareplus.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfirmOTPResponse {
    @JsonProperty("errorMessage")
    private String errorMessage;
    @JsonProperty("errorCode")
    private String errorCode;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
