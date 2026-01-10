package com.ecommerce.service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.UserDTO.UserResponse;
import com.ecommerce.entity.User;
import com.ecommerce.entity.UserRole;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.repository.UserRepository;

import jakarta.transaction.Transactional;


@Service
public class UserService {

	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    private static final ZoneId INDIA = ZoneId.of("Asia/Kolkata");


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public User register(String email, String rawPassword,UserRole role) {
    	
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("Email already registered");
        }
        
        User user = new User();
        
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        user.setRole(role != null ? role : UserRole.CUSTOMER);
        user.setCreatedAt(OffsetDateTime.now(INDIA));
        
        return userRepository.save(user);
    }

    @Transactional
    public User authenticate(String email, String password) {
        var userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isEmpty()) {
            throw new BadRequestException("Invalid email or password");
        }

        var user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new BadRequestException("Invalid Password");
        }

        return user;
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(
            user.getId(),
            user.getEmail(),
            user.getRole(),
            user.getCreatedAt()
           
        );
    }
    
    
    public User updateEntity(User entity) {
        if (!userRepository.existsByEmail(entity.getEmail())) {
            throw new BadRequestException("User not found for update!");
        }
        return userRepository.save(entity);
    }

    public void deleteEntity(Long id) {
        if (!userRepository.existsById(id)) {
            throw new BadRequestException("User does not exist!");
        }
        userRepository.deleteById(id);
    }

    public List<User> fetchAll() {
        if (userRepository.count() == 0) {
            throw new BadRequestException("No users found");
        }
        return userRepository.findAll();
    }

    public Optional<User> fetchById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public Optional<User> fetchByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}