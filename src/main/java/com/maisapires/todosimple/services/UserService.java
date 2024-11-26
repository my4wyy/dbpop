package com.maisapires.todosimple.services;

import com.maisapires.todosimple.models.User;
import com.maisapires.todosimple.models.UserProfile;
import com.maisapires.todosimple.repositories.UserProfileRepository;
import com.maisapires.todosimple.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public void updatePassword(Long userId, String newPassword) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public void deleteUser(Long userId) {
        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        
        if (userProfile != null) {
            userProfileRepository.delete(userProfile);
        }

        userRepository.deleteById(userId);
    }

    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        UserProfile profile = new UserProfile();
        profile.setUser(savedUser);
        userProfileRepository.save(profile);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null); // Retorna null se não encontrar
    }
    public User updateUser(Long id, User user) {
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (!existingUserOptional.isPresent()) {
            throw new RuntimeException("Usuário não encontrado");
        }
    
        User existingUser = existingUserOptional.get();
        existingUser.setUsername(user.getUsername());
        existingUser.setRole(user.getRole());
        existingUser.setPassword(user.getPassword()); // Atualize a senha conforme necessário
    
        return userRepository.save(existingUser);
    }
    
}
