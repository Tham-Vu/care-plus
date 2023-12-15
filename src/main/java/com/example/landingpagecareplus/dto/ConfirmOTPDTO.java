package com.example.landingpagecareplus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ConfirmOTPDTO {
    private String phone_number;
    private String otp;
}
