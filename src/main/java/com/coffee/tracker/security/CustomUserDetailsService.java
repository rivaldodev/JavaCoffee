package com.coffee.tracker.security;

import com.coffee.tracker.model.User;
import com.coffee.tracker.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository){ this.userRepository=userRepository; }
    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { Long id = Long.valueOf(username); User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found")); return new org.springframework.security.core.userdetails.User(String.valueOf(user.getId()), user.getPassword(), List.of(new SimpleGrantedAuthority("USER"))); }
}
