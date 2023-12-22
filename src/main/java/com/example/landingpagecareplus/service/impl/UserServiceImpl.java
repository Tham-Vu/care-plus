package com.example.landingpagecareplus.service.impl;

import com.example.landingpagecareplus.dto.UserDTO;
import com.example.landingpagecareplus.dto.UserPackageDTO;
import com.example.landingpagecareplus.entity.User;
import com.example.landingpagecareplus.entity.UserPackage;
import com.example.landingpagecareplus.repository.UserPackageRepository;
import com.example.landingpagecareplus.repository.UserRepository;
import com.example.landingpagecareplus.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserPackageRepository userPackageRepository;

    public UserServiceImpl(UserRepository repository, UserPackageRepository userPackageRepository) {
        this.repository = repository;
        this.userPackageRepository = userPackageRepository;
    }

    @Override
    public User save(UserDTO userDTO) {
        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return repository.save(user);
    }

    @Override
    public UserPackage createUserAndUserPackage(UserPackageDTO dto) {
        User user = repository.findOneByPhoneNumber(dto.getPhoneNumber());
        if (user == null){
            user = new User();
            user.setFullName(dto.getFullName());
            user.setPhoneNumber(dto.getPhoneNumber());
        }else {
            user.setFullName(dto.getFullName());
            user.setPhoneNumber(dto.getPhoneNumber());
        }
        User savedUser = repository.save(user);
        UserPackage userPackage = new UserPackage();
        userPackage.setPackages(dto.getPackages());
        userPackage.setUser(savedUser);
        UserPackage savedUserPackage = userPackageRepository.save(userPackage);
        return savedUserPackage;
    }

}
