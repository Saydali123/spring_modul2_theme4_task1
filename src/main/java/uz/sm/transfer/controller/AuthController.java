package uz.sm.transfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uz.sm.transfer.config.jwt.JwtUtils;
import uz.sm.transfer.dto.LoginDto;
import uz.sm.transfer.service.UserDetailService;

@RestController
public class AuthController {

    final
    UserDetailService userDetailService;
    final
    AuthenticationManager authenticationManager;
    final
    JwtUtils jwtUtils;

    public AuthController(UserDetailService userDetailService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userDetailService = userDetailService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/")
    public String homePage() {
        return "home page";
    }

    @GetMapping("/auth/login")
    public String login(@RequestBody LoginDto loginDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()));

        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return jwtUtils.generateToken(loginDto.getUsername());
    }
}
