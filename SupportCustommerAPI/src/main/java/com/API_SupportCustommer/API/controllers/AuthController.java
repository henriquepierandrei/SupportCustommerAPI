package com.API_SupportCustommer.API.controllers;

import com.API_SupportCustommer.API.dto.LoginDto;
import com.API_SupportCustommer.API.dto.RegisterDto;
import com.API_SupportCustommer.API.dto.ResponseDto;
import com.API_SupportCustommer.API.infra.security.TokenService;
import com.API_SupportCustommer.API.model.UserModel;
import com.API_SupportCustommer.API.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto){
        UserModel userModel = userRepository.findByEmail(loginDto.email()).orElseThrow(() -> new RuntimeException("User Not Found"));
        if (passwordEncoder.matches(loginDto.password(), userModel.getPassword())){
            String token = this.tokenService.generateToken(userModel);
            return ResponseEntity.ok(new ResponseDto(userModel.getUsername(),userModel.getEmail(), token));
        }
        return ResponseEntity.badRequest().build();

    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDto registerDto){
        Optional<UserModel> userModel = userRepository.findByEmail(registerDto.email());
        if (userModel.isEmpty()){

            UserModel newUser = new UserModel();
            newUser.setEmail(registerDto.email());
            newUser.setPassword(passwordEncoder.encode(registerDto.password()));
            newUser.setUsername(registerDto.name());
            this.userRepository.save(newUser);
            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDto(newUser.getUsername(), newUser.getEmail(), token));
        }

        return ResponseEntity.badRequest().build();

    }


    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok("Logout bem-sucedido");
    }
}
