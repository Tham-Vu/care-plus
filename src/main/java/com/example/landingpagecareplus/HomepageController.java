package com.example.landingpagecareplus;

import com.example.landingpagecareplus.dto.ConfirmOTPDTO;
import com.example.landingpagecareplus.dto.CreateOTPByPhoneDTO;
import com.example.landingpagecareplus.dto.GetOTPDTO;
import com.example.landingpagecareplus.dto.response.ConfirmOTPResponse;
import com.example.landingpagecareplus.dto.response.CreateOTPByPhoneResponse;
import com.example.landingpagecareplus.dto.response.GetOTPResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class HomepageController {
    private final UserService userService;
    private final WebClient webClient;

    public HomepageController(UserService userService, WebClient webClient) {
        this.userService = userService;
        this.webClient = webClient;
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
    @PostMapping("/api/v1/package/register")
    public @ResponseBody Mono<CreateOTPByPhoneResponse> createOTPByPhoneNumber(@RequestBody CreateOTPByPhoneDTO data){
        return webClient.post()
                .uri("http://10.144.13.155:7020/api/v1/package/register")
                .body(Mono.just(data), CreateOTPByPhoneDTO.class)
                .retrieve()
                .bodyToMono(CreateOTPByPhoneResponse.class);
    }
    @PostMapping("api/v1/sps/internal/package/getOTP")
    public @ResponseBody Mono<GetOTPResponse> getOTP(@RequestBody GetOTPDTO data){
        return webClient.post()
                .uri("http://10.144.26.57:7013/api/v1/sps/internal/package/getOTP")
                .body(Mono.just(data), GetOTPDTO.class)
                .retrieve()
                .bodyToMono(GetOTPResponse.class);
    }
    @PostMapping("/api/v1/package/confirm")
    public @ResponseBody Mono<ConfirmOTPResponse> confirmOTP(@RequestBody ConfirmOTPDTO data){
        return webClient.post()
                .uri("http://10.144.13.155:7020/api/v1/package/confirm")
                .body(Mono.just(data), ConfirmOTPDTO.class)
                .retrieve()
                .bodyToMono(ConfirmOTPResponse.class);
    }
}
