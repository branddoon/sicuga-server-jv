package com.sicuga.sicugaserver.controller;

import com.sicuga.sicugaserver.dto.GeneralUserAuthDTO;
import com.sicuga.sicugaserver.dto.LoginUserRequestDTO;
import com.sicuga.sicugaserver.service.GeneralUserAuthServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class GeneralUserAuthController {

    @Autowired
    GeneralUserAuthServiceImpl generalUserAuthService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUserRequestDTO loginUserDTO){
       return generalUserAuthService.validateLogin(loginUserDTO);
    }
    @PostMapping("/newUser")
    public ResponseEntity<?> registerUser(@Valid @RequestBody GeneralUserAuthDTO userDTO){
        return generalUserAuthService.saveUser(userDTO);
    }

    @GetMapping("/renew")
    public ResponseEntity<?> renewToken(){
        return generalUserAuthService.renewToken();
    }
}
