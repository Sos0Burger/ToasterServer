package com.sosoburger.toaster.service.impl;

import com.sosoburger.toaster.dao.RoleDAO;
import com.sosoburger.toaster.dao.UserDAO;
import com.sosoburger.toaster.dto.rq.RequestUserDTO;
import com.sosoburger.toaster.exception.AlreadyExistsException;
import com.sosoburger.toaster.exception.NotFoundException;
import com.sosoburger.toaster.repository.RoleRepository;
import com.sosoburger.toaster.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDAO user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Пользователь не найден с такой почтой: " + email));

        Set<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                authorities);
    }

    public UserDAO create(RequestUserDTO requestUserDTO){
        if(!userRepository.existsByEmail(requestUserDTO.getEmail())){
            if(roleRepository.findByName("ROLE_USER").isEmpty()){
                throw new NotFoundException("Такой роли не существует");
            }
            RoleDAO roles = roleRepository.findByName("ROLE_USER").get();
            return userRepository.save(new UserDAO(
                    null,
                    requestUserDTO.getEmail(),
                    passwordEncoder.encode(requestUserDTO.getPassword()),
                    Collections.singleton(roles),
                    null));
        }
        throw new AlreadyExistsException("Пользователь с такой почтой уже зарегистрирован");
    }

    public UserDAO findById(Integer id) {
        if (userRepository.existsById(id)) {
            return userRepository.findById(id).get();
        }
        throw new NotFoundException("Пользователь не найден");
    }

    public UserDAO findByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            return userRepository.findByEmail(email).get();
        }
        throw new NotFoundException("Пользователь не найден");
    }

    public UserDAO updatePassword(UserDAO user, String password) {
        user.setPassword(password);
        return userRepository.save(user);
    }

}
