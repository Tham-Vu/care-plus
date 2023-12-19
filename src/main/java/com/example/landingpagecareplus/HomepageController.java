package com.example.landingpagecareplus;

import com.example.landingpagecareplus.dto.ConfirmOTPDTO;
import com.example.landingpagecareplus.dto.CreateOTPByPhoneDTO;
import com.example.landingpagecareplus.dto.GetOTPDTO;
import com.example.landingpagecareplus.dto.response.ConfirmOTPResponse;
import com.example.landingpagecareplus.dto.response.CreateOTPByPhoneResponse;
import com.example.landingpagecareplus.dto.response.GetOTPResponse;
import com.example.landingpagecareplus.dto.response.GetOTPSuccessResponse;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import com.google.gson.Gson;

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
    public @ResponseBody CreateOTPByPhoneResponse createOTPByPhoneNumber(@RequestBody CreateOTPByPhoneDTO data){
        Gson gson = new Gson();
        String jsonBody = gson.toJson(data);
        String response = connectSSHServer(jsonBody, "http://10.144.13.155:7020/api/v1/package/register");
        CreateOTPByPhoneResponse createOTPByPhoneResponse = gson.fromJson(response, CreateOTPByPhoneResponse.class);
        return createOTPByPhoneResponse;
    }
    @PostMapping("api/v1/sps/internal/package/getOTP")
    public @ResponseBody GetOTPSuccessResponse getOTP(@RequestBody GetOTPDTO data){
        Gson gson = new Gson();
        String jsonBody = gson.toJson(data);
        String response = connectSSHServer(jsonBody, "http://10.144.26.57:7013/api/v1/sps/internal/package/getOTP");
        GetOTPSuccessResponse responseObject = gson.fromJson(response, GetOTPSuccessResponse.class);
        return responseObject;
    }

    @PostMapping("/api/v1/package/confirm")
    public @ResponseBody ConfirmOTPResponse confirmOTP(@RequestBody ConfirmOTPDTO data){
        Gson gson = new Gson();
        String jsonBody = gson.toJson(data);
        String response = connectSSHServer(jsonBody, "http://10.144.13.155:7020/api/v1/package/confirm");
        ConfirmOTPResponse confirmOTPResponse = gson.fromJson(response, ConfirmOTPResponse.class);
        return confirmOTPResponse;
    }
    private String connectSSHServer(String data, String api){
        String host = "10.144.13.155";
        String username = "root";
        String password = "6#Zh$$f0G3Q$ll$a";
        int port = 22;
        Session session = null;
        ChannelExec channel = null;
        String response = null;
        try {
            JSch jsch = new JSch();

            // Tạo session
            session = jsch.getSession(username, host, port);
            session.setPassword(password);

            // Bỏ qua kiểm tra đối chứng SSH
            session.setConfig("StrictHostKeyChecking", "no");

            // Mở kết nối SSH
            session.connect();

            // Thực hiện lệnh SSH
            String command = "curl -XPOST -H \"Content-type: application/json\" -d '" + data + "' '" + api + "'";
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);

            // Kết nối channel
            channel.connect();

            // Đọc output từ channel
            InputStream in = channel.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            response =  responseBuilder.toString();

        } catch (JSchException | IOException e) {
            e.printStackTrace();
        }finally {
            // Đóng channel và session
            channel.disconnect();
            session.disconnect();
        }
        return response;
    }
}
