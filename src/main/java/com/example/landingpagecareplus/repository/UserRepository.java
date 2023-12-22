package com.example.landingpagecareplus.repository;

import com.example.landingpagecareplus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByPhoneNumber(String phoneNumber);
}
