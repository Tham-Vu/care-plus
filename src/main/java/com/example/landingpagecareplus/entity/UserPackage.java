package com.example.landingpagecareplus.entity;

import com.example.landingpagecareplus.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class UserPackage {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "user_package_id_Sequence")
    @SequenceGenerator(name = "user_package_id_Sequence", sequenceName = "USER_PACKAGE_ID_SEQ", allocationSize=1)
    private Long id;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    private String packages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }
}
