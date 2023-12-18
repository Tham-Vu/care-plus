package com.example.landingpagecareplus.dto;
public class CreateOTPByPhoneDTO {
    private String phone_number;
    private String package_name;

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }
}
