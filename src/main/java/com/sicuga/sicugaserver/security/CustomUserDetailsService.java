package com.sicuga.sicugaserver.security;

import com.sicuga.sicugaserver.entity.GeneralUserAuth;
import com.sicuga.sicugaserver.exception.GlobalException;
import com.sicuga.sicugaserver.repository.GeneralUserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private GeneralUserAuthRepository generalUserAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        GeneralUserAuth userAuth = generalUserAuthRepository.findByEmail(email)
                .orElseThrow(()->new GlobalException("The following email was not found.:" + email, HttpStatus.INTERNAL_SERVER_ERROR));
        return new User(userAuth.getEmail(),userAuth.getPassword(),true,true,true,true,new ArrayList<>());
    }
}