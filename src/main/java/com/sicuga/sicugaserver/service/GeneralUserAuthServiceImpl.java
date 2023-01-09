package com.sicuga.sicugaserver.service;

import com.sicuga.sicugaserver.dto.GeneralUserAuthDTO;
import com.sicuga.sicugaserver.dto.JWTAuthResponseDTO;
import com.sicuga.sicugaserver.dto.LoginUserRequestDTO;
import com.sicuga.sicugaserver.entity.GeneralUserAuth;
import com.sicuga.sicugaserver.exception.GlobalException;
import com.sicuga.sicugaserver.repository.GeneralUserAuthRepository;
import com.sicuga.sicugaserver.security.JwtTokenProvider;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class GeneralUserAuthServiceImpl {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    GeneralUserAuthRepository generalUserAuthRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<?> validateLogin(LoginUserRequestDTO loginUserDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDTO.getEmail(),loginUserDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        GeneralUserAuth userAuth = findUserByEmail(loginUserDTO.getEmail());
        return new ResponseEntity<>(mapperAuthResponse(token, userAuth),HttpStatus.OK);
    }

    public ResponseEntity<?> saveUser(GeneralUserAuthDTO userDTO){
        GeneralUserAuth userAuth = modelMapper.map(userDTO, GeneralUserAuth.class);
        if(generalUserAuthRepository.existsByEmail(userAuth.getEmail())){
            throw new GlobalException("Email already exists.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        userAuth.setPassword(bCryptPasswordEncoder.encode(userAuth.getPassword()));
        generalUserAuthRepository.save(userAuth);
        String token = jwtTokenProvider.renewToken(userAuth.getEmail());
        return new ResponseEntity<>(mapperAuthResponse(token,userAuth), HttpStatus.OK);
    }

    public GeneralUserAuth findUserByEmail(String email){
        return generalUserAuthRepository.findByEmail(email)
                .orElseThrow(()->new GlobalException("User was not found.", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    public GeneralUserAuth findUserById(String id){
        return generalUserAuthRepository.findById(id)
                .orElseThrow(()->new GlobalException("User was not found.", HttpStatus.INTERNAL_SERVER_ERROR));
    }
    public ResponseEntity<?> renewToken(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String token = jwtTokenProvider.renewToken(email);
        GeneralUserAuth userAuth = findUserByEmail(email);
        return new ResponseEntity<>(mapperAuthResponse(token,userAuth), HttpStatus.OK);
    }

    private JWTAuthResponseDTO mapperAuthResponse(String token, GeneralUserAuth userAuth){
        JWTAuthResponseDTO authResponse = new JWTAuthResponseDTO();
        authResponse.setId(userAuth.getId());
        authResponse.setName(userAuth.getName());
        authResponse.setEmail(userAuth.getEmail());
        authResponse.setToken(token);
        return authResponse;
    }

}
