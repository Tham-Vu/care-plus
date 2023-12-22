package com.example.landingpagecareplus.repository;

import com.example.landingpagecareplus.entity.UserPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPackageRepository extends JpaRepository<UserPackage, Long> {
}
