package com.sicuga.sicugaserver.controller;

import com.sicuga.sicugaserver.dto.GeneralUserAuthDTO;
import com.sicuga.sicugaserver.dto.JWTAuthResponseDTO;
import com.sicuga.sicugaserver.dto.LoginUserRequestDTO;
import com.sicuga.sicugaserver.entity.GeneralUserAuth;
import com.sicuga.sicugaserver.security.JwtTokenProvider;
import com.sicuga.sicugaserver.service.GeneralUserAuthServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class GeneralUserAuthController {

    @Autowired
    GeneralUserAuthServiceImpl generalUserAuthService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserRequestDTO loginUserDTO){
       return generalUserAuthService.validateLogin(loginUserDTO);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody GeneralUserAuthDTO userDTO){
        return generalUserAuthService.saveUser(userDTO);
    }

    @GetMapping("/renew")
    public ResponseEntity<?> renewToken(){
        return generalUserAuthService.renewToken();
    }
}
