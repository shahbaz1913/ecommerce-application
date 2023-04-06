package com.ecommerce.appliaction.serviceimpl;

import com.ecommerce.appliaction.dto.UserDTO;
import com.ecommerce.appliaction.repositotry.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User Name " + username + "Not Found");
        }
        return new User(userRepository.findByUsername(username).getUsername(), userRepository.findByUsername(username).getPassword(),
                new ArrayList<>());

    }

}
