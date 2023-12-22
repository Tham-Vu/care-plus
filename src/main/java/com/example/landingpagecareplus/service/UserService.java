package com.example.landingpagecareplus.service;

import com.example.landingpagecareplus.dto.UserDTO;
import com.example.landingpagecareplus.dto.UserPackageDTO;
import com.example.landingpagecareplus.entity.User;
import com.example.landingpagecareplus.entity.UserPackage;

public interface UserService {
    User save (UserDTO userDTO);
    UserPackage createUserAndUserPackage(UserPackageDTO dto);
}
