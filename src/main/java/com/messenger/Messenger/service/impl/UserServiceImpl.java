package com.messenger.Messenger.service.impl;

import com.messenger.Messenger.dto.RequestUserDTO;
import com.messenger.Messenger.repository.UserRepository;
import com.messenger.Messenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void create(RequestUserDTO requestUserDTO) {
        userRepository.save(requestUserDTO.toDAO());
    }
}
