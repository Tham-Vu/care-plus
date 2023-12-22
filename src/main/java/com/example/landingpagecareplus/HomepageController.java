package com.example.landingpagecareplus;

import com.example.landingpagecareplus.dto.ConfirmOTPDTO;
import com.example.landingpagecareplus.dto.CreateOTPByPhoneDTO;
import com.example.landingpagecareplus.dto.GetOTPDTO;
import com.example.landingpagecareplus.dto.UserPackageDTO;
import com.example.landingpagecareplus.dto.response.ConfirmOTPResponse;
import com.example.landingpagecareplus.dto.response.CreateOTPByPhoneResponse;

import com.example.landingpagecareplus.dto.response.GetOTPSuccessResponse;
import com.example.landingpagecareplus.entity.UserPackage;
import com.example.landingpagecareplus.service.UserService;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.google.gson.Gson;

@Controller
public class HomepageController {
    @Value("${ssh.host}")
    private String host;
    @Value("${ssh.username}")
    private String username;
    @Value("${ssh.password}")
    private String password;
    @Value("${ssh.port}")
    private int port;
    private final UserService userService;

    public HomepageController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-user")
    public @ResponseBody ResponseEntity<UserPackage> createUser(@RequestBody UserPackageDTO userPackageDTO){
        return ResponseEntity.ok().body(userService.createUserAndUserPackage(userPackageDTO));
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
