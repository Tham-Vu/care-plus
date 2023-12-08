package com.example.landingpagecareplus;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class HomepageController {
    private final UserService userService;

    public HomepageController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"/", "/homepage"}, method = RequestMethod.GET)
    public String homepage(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "index";
    }
    @PostMapping("/create-user")
    public String createUser(@RequestBody User inputUser){
        User user = userService.findByPhoneNumber(inputUser.getPhoneNumber());
        if (user == null){
            user = new User();
        }else {
            user.setId(user.getId());
        }
        user.setName(inputUser.getName());
        user.setOtpCode(inputUser.getOtpCode());
        user.setServices(inputUser.getServices());
        user.setPhoneNumber(inputUser.getPhoneNumber());
        User newUser = userService.save(user);
        System.out.println(newUser.toString());
        return "redirect:/homepage";
    }
}
