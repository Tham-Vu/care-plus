package com.example.landingpagecareplus.controller;

import com.example.landingpagecareplus.dto.UserDTO;
import com.example.landingpagecareplus.entity.User;
import com.example.landingpagecareplus.repository.UserRepository;
import com.example.landingpagecareplus.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController("/users")
public class UserController {
    private final UserRepository repository;
    private final UserService userService;

    public UserController(UserRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        return ResponseEntity.ok().body(repository.findById(id).get());
    }
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody UserDTO dto){
        return ResponseEntity.ok().body(userService.save(dto));
    }
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody UserDTO dto) throws IOException {
        User user = repository.findById(dto.getId()).get();
        if (user.getId() == null){
            throw new IOException("User doesn't exist");
        }
        return ResponseEntity.ok().body(userService.save(dto));
    }
    @DeleteMapping("/delete")
    public void deleteById(@RequestBody Long id){
        repository.deleteById(id);
    }
}
