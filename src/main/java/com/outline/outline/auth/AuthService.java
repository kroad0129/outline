package com.outline.outline.auth;

import com.outline.outline.user.User;
import com.outline.outline.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public User login(String username) {
        return userRepository.findByUsername(username)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .username(username)
                                .createdAt(LocalDateTime.now())
                                .build()
                ));
    }
}
