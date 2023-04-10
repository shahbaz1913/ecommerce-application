package com.ecommerce.appliaction.controller;

import com.ecommerce.appliaction.dto.UserDTO;
import com.ecommerce.appliaction.entity.User;
import com.ecommerce.appliaction.repositotry.UserRepository;
import com.ecommerce.appliaction.serviceimpl.CustomUserDetailsService;
import com.ecommerce.appliaction.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private JwtUtil JwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> createAuthenticationToken(@RequestBody UserDTO user) throws Exception {
        try {
            log.info("UserController :: createAuthenticationToken method called");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails = userDetailsService
                    .loadUserByUsername(user.getUsername());
            String token = JwtUtil.generateToken(userDetails);
            log.info("Credentials are correct,login successfully ");
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            log.error(e.getMessage());
            log.error("BadCredentialsException exception");
            throw e;

        }
    }


    @PostMapping("sing")
    public User createNewUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        return this.userRepository.save(user);
    }

}
