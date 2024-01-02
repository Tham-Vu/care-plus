package com.example.landingpagecareplus.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "user_id_Sequence")
    @SequenceGenerator(name = "user_id_Sequence", sequenceName = "USER_ID_SEQ", allocationSize=1)
    private Long id;
    private String fullName;
    @Column(unique = true)
    private String phoneNumber;
    private Date dateOfBirth;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserPackage> userPackages;

    public User() {
    }

    public User(Long id, String fullName, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<UserPackage> getUserPackages() {
        return userPackages;
    }

    public void setUserPackages(Set<UserPackage> userPackages) {
        this.userPackages = userPackages;
    }
}
