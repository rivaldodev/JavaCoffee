package com.coffee.tracker.service;

import com.coffee.tracker.dto.UserRequest;
import com.coffee.tracker.dto.UserResponse;
import com.coffee.tracker.dto.UserUpdateRequest;
import com.coffee.tracker.exception.EmailAlreadyUsedException;
import com.coffee.tracker.exception.ResourceNotFoundException;
import com.coffee.tracker.model.User;
import com.coffee.tracker.repository.CoffeeLogRepository;
import com.coffee.tracker.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository; private final CoffeeLogRepository coffeeLogRepository; private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, CoffeeLogRepository coffeeLogRepository, PasswordEncoder passwordEncoder){ this.userRepository=userRepository; this.coffeeLogRepository=coffeeLogRepository; this.passwordEncoder=passwordEncoder; }
    @Transactional public User create(UserRequest request){ if(userRepository.existsByEmail(request.getEmail())) throw new EmailAlreadyUsedException("Email já cadastrado"); User user=new User(request.getName(), request.getEmail(), passwordEncoder.encode(request.getPassword())); return userRepository.save(user);} 
    public UserResponse getById(Long id){ return userRepository.findDtoById(id).orElseThrow(()-> new ResourceNotFoundException("Usuário não encontrado")); }
    public User getEntity(Long id){ return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Usuário não encontrado")); }
    @Transactional public User update(Long id, UserUpdateRequest request){
        User user = getEntity(id);
        // Email
        if(request.getEmail()!=null && !request.getEmail().isBlank()){
            if(!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail()))
                throw new EmailAlreadyUsedException("Email já cadastrado");
            user.setEmail(request.getEmail());
        }
        // Nome
        if(request.getName()!=null && !request.getName().isBlank()){
            user.setName(request.getName());
        }
        // Senha
        if(request.getPassword()!=null && !request.getPassword().isBlank()){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        return userRepository.save(user);
    }
    @Transactional public void delete(Long id){ if(!userRepository.existsById(id)) throw new ResourceNotFoundException("Usuário não encontrado"); userRepository.deleteById(id);} 
    public Page<UserResponse> list(Pageable pageable){ return userRepository.findAllWithDrinkCounter(pageable); }
}
