package com.example.effectivemobiletest.security;

import com.example.effectivemobiletest.exception.TaskNotFoundException;
import com.example.effectivemobiletest.exception.UserNotFoundException;
import com.example.effectivemobiletest.model.SecurityUser;
import com.example.effectivemobiletest.model.User;
import com.example.effectivemobiletest.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username)  {
        Optional<User> user = userRepository.findUserByUsername(username);

        return user
                .map(SecurityUser::new)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}