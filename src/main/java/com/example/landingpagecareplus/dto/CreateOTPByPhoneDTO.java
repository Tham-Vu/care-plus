package com.example.landingpagecareplus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOTPByPhoneDTO {
    private String phone_number;
    private String package_name;
}
