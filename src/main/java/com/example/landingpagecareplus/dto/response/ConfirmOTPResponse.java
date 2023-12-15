package com.example.landingpagecareplus.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmOTPResponse {
    private String errorMessage;
    private String errorCode;
}
